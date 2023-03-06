package com.lira.cinetime.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.core.createDialog
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.data.models.firebase.User
import com.lira.cinetime.databinding.FragmentAccountBinding
import com.lira.cinetime.presentation.account.AccountViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val accountViewModel by viewModel<AccountViewModel>()
    private val dialog by lazy { createProgressDialog() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountViewModel.fireUser.collectLatest {
                    when(it) {
                        AccountViewModel.State.Loading -> {
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                            dialog.show()
                        }
                        is AccountViewModel.State.Success -> {
                            dialog.dismiss()
                            setupUI(it.user)
                        }
                        is AccountViewModel.State.Error -> {
                            createDialog {
                                setMessage(it.error.message)
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
                    .into(ivProfileImg)
            } else
                ivProfileImg.setImageResource(R.drawable.person_placeholder)

            tvName.text = user.name

            tvEmail.text = user.email
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}