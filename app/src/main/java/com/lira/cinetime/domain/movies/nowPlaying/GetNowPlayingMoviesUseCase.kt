package com.lira.cinetime.domain.movies.nowPlaying

import androidx.paging.PagingData
import com.lira.cinetime.data.models.movies.nowPlaying.NowPlayingResult
import com.lira.cinetime.data.repositories.moviesRepository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetNowPlayingMoviesUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Flow<PagingData<NowPlayingResult>> {
        return moviesRepository.getNowPlayingMovies()
    }

}