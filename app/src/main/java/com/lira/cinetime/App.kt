package com.lira.cinetime

import android.app.Application
import com.lira.cinetime.data.di.DataModule
import com.lira.cinetime.domain.di.DomainModule
import com.lira.cinetime.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }

}