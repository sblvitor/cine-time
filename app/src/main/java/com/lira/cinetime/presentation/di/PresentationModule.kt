package com.lira.cinetime.presentation.di

import com.lira.cinetime.presentation.LoginViewModel
import com.lira.cinetime.presentation.MainViewModel
import com.lira.cinetime.presentation.PopularMoviesViewModel
import com.lira.cinetime.presentation.RegisterViewModel
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
            viewModel { MainViewModel(get(), get()) }
            viewModel { LoginViewModel(get()) }
            viewModel { RegisterViewModel(get()) }
            viewModel { PopularMoviesViewModel(get(), get()) }
        }
    }

}