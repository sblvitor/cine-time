package com.lira.cinetime.data.di

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.lira.cinetime.data.firebase.ServiceAuthRepository
import com.lira.cinetime.data.firebase.ServiceAuthRepositoryImpl
import com.lira.cinetime.data.moviesRepository.PopularMoviesRepository
import com.lira.cinetime.data.moviesRepository.PopularMoviesRepositoryImpl
import com.lira.cinetime.data.services.TheMoviesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    private const val OK_HTTP: String = "OkHttp"

    fun load(){
        loadKoinModules(firebaseAuthModule() + firebaseRepositoryModule() + networkModules() + repositoriesModule())
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.d(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
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
            single<PopularMoviesRepository> { PopularMoviesRepositoryImpl(get()) }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
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