package com.lira.cinetime.ui.authFlow.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.lira.cinetime.R
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.databinding.FragmentLoginBinding
import com.lira.cinetime.presentation.authFlow.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null
    private val dialog by lazy { createProgressDialog() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                LoginViewModel.State.Loading -> {
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.show()
                }
                is LoginViewModel.State.Error -> {
                    dialog.dismiss()
                    binding.tvWrongCredentialsLogin.visibility = View.VISIBLE
                }
                is LoginViewModel.State.Success -> {
                    binding.tvWrongCredentialsLogin.visibility = View.INVISIBLE
                    dialog.dismiss()
                    findNavController().navigate(R.id.action_nav_login_to_nav_movies)
                }
            }
        }

        binding.apply {
            btnRegisterFromLogin.setOnClickListener {
                it.findNavController().navigate(R.id.action_nav_login_to_nav_register)
            }

            btnConfirmLogin.setOnClickListener {
                loginViewModel.logIn(etEmailLogin.text.toString(), etPasswordLogin.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}