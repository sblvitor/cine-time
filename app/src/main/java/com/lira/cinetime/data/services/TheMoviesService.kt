package com.lira.cinetime.data.services

import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.data.models.movies.nowPlaying.NowPlayingResponse
import com.lira.cinetime.data.models.movies.popularMovies.PopularMoviesResponse
import com.lira.cinetime.data.models.movies.topRated.TopRatedResponse
import com.lira.cinetime.data.models.tvShows.airingToday.AiringTodayTvResponse
import com.lira.cinetime.data.models.tvShows.popularTv.PopularTvResponse
import com.lira.cinetime.data.models.tvShows.topRated.TopRatedTvResponse
import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Long,
                                @Query("api_key") api_key: String,
                                @Query("language") language: String,
                                @Query("append_to_response") append_to_response: String): MovieDetailsResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("api_key") api_key: String,
                                  @Query("language") language: String,
                                  @Query("page") page: Int): PopularTvResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTv(@Query("api_key") api_key: String,
                                 @Query("language") language: String,
                                 @Query("page") page: Int): AiringTodayTvResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(@Query("api_key") api_key: String,
                              @Query("language") language: String,
                              @Query("page") page: Int): TopRatedTvResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(@Path("tv_id") tvId: Long,
                             @Query("api_key") api_key: String,
                             @Query("language") language: String,
                             @Query("append_to_response") append_to_response: String): TvDetailsResponse

}