package com.lira.cinetime.ui.authFlow.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lira.cinetime.R
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.databinding.FragmentRegisterBinding
import com.lira.cinetime.presentation.authFlow.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val registerViewModel by viewModel<RegisterViewModel>()
    private var _binding: FragmentRegisterBinding? = null
    private val dialog by lazy { createProgressDialog() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                RegisterViewModel.State.Loading -> {
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.show()
                }
                is RegisterViewModel.State.Error -> {
                    dialog.dismiss()
                    Snackbar.make((requireActivity()).findViewById(android.R.id.content), "${it.error.message}", Snackbar.LENGTH_LONG).show()
                }
                is RegisterViewModel.State.Success -> {
                    binding.tvWrongCredentialsRegister.visibility = View.GONE
                    dialog.dismiss()
                    findNavController().navigate(R.id.action_nav_register_to_nav_movies)
                }
                RegisterViewModel.State.EmptyFields -> {
                    binding.tvWrongCredentialsRegister.visibility = View.VISIBLE
                }
            }
        }

        binding.apply {
            btnLoginFromRegister.setOnClickListener {
                it.findNavController().navigate(R.id.action_nav_register_to_nav_login)
            }

            btnConfirmRegister.setOnClickListener {
                registerViewModel.createAccount(etEmailRegister.text.toString(), etPasswordRegister.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}