package com.example.domain.tvshows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import com.example.domain.movies.INITIAL_MOVIES_PAGE
import com.example.model.tvshow.TvShow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val INITIAL_TVSHOWS_PAGE = 1

class TvShowsPagingSource @Inject constructor(
    private val tvShoesStore: TvShowsStore
)  : PagingSource<Int, TvShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val pageNumber = params.key ?: INITIAL_TVSHOWS_PAGE
            val tvShows = tvShoesStore.getTvShowsForPage(pageNumber) ?: emptyList()

            val prevKey = if (pageNumber > INITIAL_MOVIES_PAGE) pageNumber - 1 else null
            val nextKey = if (tvShows.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = tvShows,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}