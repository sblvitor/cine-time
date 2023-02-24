package com.lira.cinetime.data.repositories.tvShowsRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.tvShows.topRated.TopRatedTvResult
import com.lira.cinetime.data.services.TheMoviesService
import retrofit2.HttpException
import java.io.IOException

class TopRatedTvPagingSource(private val service: TheMoviesService): PagingSource<Int, TopRatedTvResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopRatedTvResult> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getTopRatedTv(
                api_key = Constants.API_KEY,
                language = Constants.LANG_PT_BR,
                page = pageIndex
            )

            val tvShows = response.results
            val nextKey = if(tvShows.isEmpty()) null else pageIndex + 1

            LoadResult.Page(
                data = tvShows,
                nextKey = nextKey,
                prevKey = if(pageIndex == 1) null else -1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopRatedTvResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}