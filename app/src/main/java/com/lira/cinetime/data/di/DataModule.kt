package com.lira.cinetime.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lira.cinetime.data.firebase.ServiceAuthRepository
import com.lira.cinetime.data.firebase.ServiceAuthRepositoryImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule {

    fun load(){
        loadKoinModules(firebaseAuthModule() + firebaseRepositoryModule())
    }

    private fun firebaseAuthModule(): Module{
        return module {
            //FirebaseAuth.getInstance()
            single { Firebase.auth }
        }
    }

    private fun firebaseRepositoryModule(): Module {
        return module {
            single<ServiceAuthRepository> { ServiceAuthRepositoryImpl(get()) }
        }
    }
}