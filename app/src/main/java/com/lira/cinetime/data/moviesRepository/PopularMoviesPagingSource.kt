package com.lira.cinetime.data.moviesRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lira.cinetime.data.models.PopularMoviesResult
import com.lira.cinetime.data.services.TheMoviesService
import retrofit2.HttpException
import java.io.IOException

class PopularMoviesPagingSource(private val service: TheMoviesService): PagingSource<Int, PopularMoviesResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMoviesResult> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getPopularMovies(
                api_key = "9d0a61f61a811455f813884fec3b0e95",
                language = "pt-BR",
                page = pageIndex
            )
            val movies = response.results
            //val nextKey = if(movies.isEmpty()) null else pageIndex + (params.loadSize / 20)
            val nextKey = pageIndex + 1

            LoadResult.Page(
                data = movies,
                // else pageIndex
                prevKey = if(pageIndex == 1) null else -1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PopularMoviesResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}