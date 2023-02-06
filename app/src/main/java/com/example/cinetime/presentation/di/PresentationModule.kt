package com.example.cinetime.presentation.di

import com.example.cinetime.presentation.LoginViewModel
import com.example.cinetime.presentation.MainViewModel
import com.example.cinetime.presentation.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            viewModel { MainViewModel() }
            viewModel { LoginViewModel() }
            viewModel { RegisterViewModel() }
        }
    }

}