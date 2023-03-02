package com.lira.cinetime.data.models.firebase

data class AllData(
    val toWatchMovies: List<Movie>,
    val favMovies: List<Movie>,
    val toWatchTvShows: List<TvShow>,
    val favTvShows: List<TvShow>
)
