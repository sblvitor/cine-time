package com.lira.cinetime.ui.account

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.lira.cinetime.R
import com.lira.cinetime.core.createDialog
import com.lira.cinetime.data.models.firebase.User
import com.lira.cinetime.databinding.FragmentEditProfileBinding
import com.lira.cinetime.presentation.account.EditProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val editProfileViewModel by viewModel<EditProfileViewModel>()
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var selectedImage: Uri? = null

    private val args: EditProfileFragmentArgs by navArgs()

    private val binding get() = _binding!!

    private val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == RESULT_OK && result.data != null){
            try {
                selectedImage = result.data!!.data
                Glide
                    .with(binding.root)
                    .load(selectedImage)
                    .placeholder(R.drawable.person_placeholder)
                    .into(binding.ivProfileImgEdit)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Falha ao carregar a imagem da galeria", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        val user = args.fireUser

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if(isGranted) {
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                        type = "image/*"
                    }
                    register.launch(galleryIntent)
                } else {
                    createDialog {
                        setMessage("Não é possível selecionar uma imagem, pois a permissão de acesso à galeria foi negada!")
                    }.show()
                }
            }

        setupUI(user)
        collectData()
    }

    private fun setupAppBar() {
        binding.editProfileToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editProfileViewModel.update.collectLatest {
                    when(it) {
                        EditProfileViewModel.State.Loading -> {}
                        EditProfileViewModel.State.Success -> {
                            binding.progressBarEditProfile.visibility = View.GONE
                            Snackbar.make(binding.root, "Perfil atualizado com sucesso!", Snackbar.LENGTH_SHORT).show()
                            binding.root.findNavController().navigateUp()
                        }
                        is EditProfileViewModel.State.Error -> {
                            binding.progressBarEditProfile.visibility = View.GONE
                            createDialog {
                                setMessage(it.error.localizedMessage)
                            }.show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUI(user: User) {
        binding.apply {

            if(user.profileImage != null) {
                Glide
                    .with(root)
                    .load(user.profileImage)
                    .placeholder(R.drawable.person_placeholder)
                    .into(ivProfileImgEdit)
            } else {
                ivProfileImgEdit.setImageResource(R.drawable.person_placeholder_add)
            }

            tvEmailEdit.text = user.email

            etNameUpdate.setText(user.name)

            ivProfileImgEdit.setOnClickListener {
                when {
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED -> {
                        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                            type = "image/*"
                        }
                        register.launch(galleryIntent)
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                        showRationaleDialog()
                    }
                    else -> {
                        requestPermissionLauncher.launch(
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            }

            btnUpdate.setOnClickListener {
                val inputName = etNameUpdate.text.toString()
                if(inputName.isNotEmpty()) {
                    when {
                        selectedImage != null -> {
                            val ext = getFileExt(selectedImage!!)
                            if(inputName != user.name) {
                                progressBarEditProfile.visibility = View.VISIBLE
                                editProfileViewModel.updateUserProfileImgAndName(user.id!!, selectedImage!!, ext, inputName)
                            }
                            else {
                                progressBarEditProfile.visibility = View.VISIBLE
                                editProfileViewModel.updateUserProfileImg(user.id!!, selectedImage!!, ext)
                            }
                        }
                        else -> {
                            if(inputName != user.name) {
                                progressBarEditProfile.visibility = View.VISIBLE
                                editProfileViewModel.updateUserName(user.id!!, inputName)
                            }
                            else
                                Toast.makeText(requireContext(),"Nada a ser alterado", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(),"Insira um nome!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showRationaleDialog() {
        val builder = activity?.let { MaterialAlertDialogBuilder(it) }
        builder!!.setMessage("In order to be able to upload an image to your profile, you have to allow the app to access photos, medias and files of your device." +
                " You can do it now in the Application Settings")
            .setPositiveButton("Go to settings") {_, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("No, thanks") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun getFileExt(uri: Uri): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(requireContext().contentResolver.getType(uri))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}