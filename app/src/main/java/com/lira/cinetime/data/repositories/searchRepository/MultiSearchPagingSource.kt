package com.lira.cinetime.data.repositories.searchRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.search.multiSearch.MultiSearchResult
import com.lira.cinetime.data.services.TheMoviesService
import retrofit2.HttpException
import java.io.IOException

class MultiSearchPagingSource(private val service: TheMoviesService, private val query: String): PagingSource<Int, MultiSearchResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MultiSearchResult> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.searchMulti(
                api_key = Constants.API_KEY,
                language = Constants.LANG_PT_BR,
                query = query,
                page = pageIndex
            )

            val searchResult = response.results
            val nextKey = if(searchResult.isEmpty()) null else pageIndex + 1

            LoadResult.Page(
                data = searchResult,
                nextKey = nextKey,
                prevKey = if(pageIndex == 1) null else -1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MultiSearchResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}