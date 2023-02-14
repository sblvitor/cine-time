package com.lira.cinetime.domain.di

import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.authFlow.LogInUseCase
import com.lira.cinetime.domain.authFlow.RegisterUseCase
import com.lira.cinetime.domain.authFlow.SignOutUseCase
import com.lira.cinetime.domain.nowPlaying.GetNowPlayingMoviesUseCase
import com.lira.cinetime.domain.popularMovies.GetPopularMoviesUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load(){
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module{
        return module {
            factory { LogInUseCase(get()) }
            factory { RegisterUseCase(get()) }
            factory { GetCurrentUserUseCase(get()) }
            factory { SignOutUseCase(get()) }
            factory { GetPopularMoviesUseCase(get()) }
            factory { GetNowPlayingMoviesUseCase(get()) }
        }
    }

}