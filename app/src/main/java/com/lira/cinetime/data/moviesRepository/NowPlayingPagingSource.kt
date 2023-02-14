package com.lira.cinetime.data.moviesRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.nowPlaying.NowPlayingResult
import com.lira.cinetime.data.services.TheMoviesService
import retrofit2.HttpException
import java.io.IOException

class NowPlayingPagingSource(private val service: TheMoviesService): PagingSource<Int, NowPlayingResult>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingResult> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getNowPlayingMovies(
                api_key = Constants.API_KEY,
                language = Constants.LANG_PT_BR,
                region = Constants.REGION_BR,
                page = pageIndex
            )

            val movies = response.results
            val nextKey = pageIndex + 1

            LoadResult.Page(
                data = movies,
                nextKey = nextKey,
                prevKey = if(pageIndex == 1) null else -1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NowPlayingResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}