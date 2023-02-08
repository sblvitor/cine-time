package com.lira.cinetime.domain.di

import com.lira.cinetime.domain.authFlow.IsConnectedUseCase
import com.lira.cinetime.domain.authFlow.LogInUseCase
import com.lira.cinetime.domain.authFlow.RegisterUseCase
import com.lira.cinetime.domain.authFlow.SignOutUseCase
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
            factory { IsConnectedUseCase(get()) }
            factory { SignOutUseCase(get()) }
        }
    }

}