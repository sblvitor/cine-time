package com.lira.cinetime.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.lira.cinetime.R
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.databinding.FragmentRegisterBinding
import com.lira.cinetime.presentation.RegisterViewModel
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
                    // paint red
                }
                is RegisterViewModel.State.Success -> {
                    dialog.dismiss()
                    findNavController().navigate(R.id.action_nav_register_to_nav_popular_movies)
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