package com.lira.cinetime.data.di

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.lira.cinetime.data.firebase.auth.ServiceAuthRepository
import com.lira.cinetime.data.firebase.auth.ServiceAuthRepositoryImpl
import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import com.lira.cinetime.data.firebase.firestore.FirestoreRepositoryImpl
import com.lira.cinetime.data.preferences.SettingsRepository
import com.lira.cinetime.data.preferences.SettingsRepositoryImpl
import com.lira.cinetime.data.repositories.moviesRepository.MoviesRepository
import com.lira.cinetime.data.repositories.moviesRepository.MoviesRepositoryImpl
import com.lira.cinetime.data.repositories.searchRepository.SearchRepository
import com.lira.cinetime.data.repositories.searchRepository.SearchRepositoryImpl
import com.lira.cinetime.data.repositories.tvShowsRepository.TvShowsRepository
import com.lira.cinetime.data.repositories.tvShowsRepository.TvShowsRepositoryImpl
import com.lira.cinetime.data.services.TheMoviesService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object DataModule {

    private const val OK_HTTP: String = "OkHttp"

    fun load(){
        loadKoinModules(firebaseInstancesModule() + firebaseRepositoriesModule() + networkModules() + repositoriesModule() + settingsRepositoryModule())
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.d(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .cache(Cache(File(androidApplication().cacheDir, "http-cache"), 10L * 1024L * 1024L)) //10MiB
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<TheMoviesService>(get(),get())
            }
        }
    }

    private fun repositoriesModule(): Module {
        return module {
            single<MoviesRepository> { MoviesRepositoryImpl(get()) }
            single<TvShowsRepository> { TvShowsRepositoryImpl(get()) }
            single<SearchRepository> { SearchRepositoryImpl(get()) }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }

    private fun firebaseInstancesModule(): Module{
        return module {
            //FirebaseAuth.getInstance()
            single { Firebase.auth }
            single { Firebase.firestore }
        }
    }

    private fun firebaseRepositoriesModule(): Module {
        return module {
            single<ServiceAuthRepository> { ServiceAuthRepositoryImpl(get()) }
            single<FirestoreRepository> { FirestoreRepositoryImpl(get()) }
        }
    }

    private fun settingsRepositoryModule(): Module {
        return module {
            single<SettingsRepository> { SettingsRepositoryImpl(get()) }
        }
    }
}