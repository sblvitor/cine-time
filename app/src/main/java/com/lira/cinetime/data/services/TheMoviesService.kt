package com.lira.cinetime.data.services

import com.lira.cinetime.data.models.nowPlaying.NowPlayingResponse
import com.lira.cinetime.data.models.popularMovies.PopularMoviesResponse
import com.lira.cinetime.data.models.topRated.TopRatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String,
                                 @Query("language") language: String,
                                 @Query("page") page: Int): PopularMoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") api_key: String,
                                    @Query("language") language: String,
                                    @Query("region") region: String,
                                    @Query("page") page: Int): NowPlayingResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") api_key: String,
                                  @Query("language") language: String,
                                  @Query("page") page: Int): TopRatedResponse

}