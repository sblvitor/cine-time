package com.lira.cinetime.domain.di

import com.lira.cinetime.domain.account.GetUserFirestoreUseCase
import com.lira.cinetime.domain.authFlow.*
import com.lira.cinetime.domain.movies.movieDetails.GetMovieDetailsUseCase
import com.lira.cinetime.domain.movies.nowPlaying.GetNowPlayingMoviesUseCase
import com.lira.cinetime.domain.movies.popularMovies.GetPopularMoviesUseCase
import com.lira.cinetime.domain.movies.topRated.GetTopRatedMoviesUseCase
import com.lira.cinetime.domain.myLists.*
import com.lira.cinetime.domain.search.GetMultiSearchResultUseCase
import com.lira.cinetime.domain.search.GetTrendingUseCase
import com.lira.cinetime.domain.tvShows.airingToday.GetAiringTodayTvUseCase
import com.lira.cinetime.domain.tvShows.popular.GetPopularTvShowsUseCase
import com.lira.cinetime.domain.tvShows.topRated.GetTopRatedTvUseCase
import com.lira.cinetime.domain.tvShows.tvDetails.GetTvDetailsUseCase
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
            factory { GetTopRatedMoviesUseCase(get()) }
            factory { GetMovieDetailsUseCase(get()) }
            factory { GetPopularTvShowsUseCase(get()) }
            factory { GetAiringTodayTvUseCase(get()) }
            factory { GetTopRatedTvUseCase(get()) }
            factory { GetTvDetailsUseCase(get()) }
            factory { GetTrendingUseCase(get()) }
            factory { GetMultiSearchResultUseCase(get()) }
            factory { AddUserToDBUseCase(get()) }
            factory { AddMovieToFavoritesUseCase(get()) }
            factory { IsMovieFavoriteUseCase(get()) }
            factory { DeleteFavoriteMovieUseCase(get()) }
            factory { AddMovieToWatchUseCase(get()) }
            factory { IsMovieInToWatchUseCase(get()) }
            factory { DeleteToWatchMovieUseCase(get()) }
            factory { AddTvToFavoritesUseCase(get()) }
            factory { IsTvFavoriteUseCase(get()) }
            factory { DeleteFavoriteTvUseCase(get()) }
            factory { AddTvToWatchUseCase(get()) }
            factory { IsTvInToWatchUseCase(get()) }
            factory { DeleteToWatchTvUseCase(get()) }
            factory { GetAllFavoriteMoviesUseCase(get()) }
            factory { GetAllToWatchMoviesUseCase(get()) }
            factory { GetAllFavoriteTvUseCase(get()) }
            factory { GetAllToWatchTvUseCase(get()) }
            factory { GetUserFirestoreUseCase(get()) }
        }
    }

}