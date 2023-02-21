package com.lira.cinetime.presentation.di

import com.lira.cinetime.presentation.*
import com.lira.cinetime.presentation.authFlow.LoginViewModel
import com.lira.cinetime.presentation.authFlow.RegisterViewModel
import com.lira.cinetime.presentation.movies.*
import com.lira.cinetime.presentation.tvShows.PopularTvViewModel
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
            viewModel { MoviesViewModel(get()) }
            viewModel { PopularMoviesViewModel(get()) }
            viewModel { NowPlayingViewModel(get()) }
            viewModel { TopRatedViewModel(get()) }
            viewModel { MovieDetailsViewModel(get()) }
            viewModel { PopularTvViewModel(get()) }
        }
    }

}