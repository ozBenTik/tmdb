package com.example.domain.movies.observers.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.model.movie.Movie
import timber.log.Timber
import java.io.IOException

const val INITIAL_MOVIES_PAGE = 1

class MoviesPagingSource(
    private val moviesStore: MoviesStore
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return try {
            val pageNumber = params.key ?: INITIAL_MOVIES_PAGE

            val movies = moviesStore.getMoviesForPage(pageNumber) ?: emptyList()

            val prevKey = if (pageNumber > INITIAL_MOVIES_PAGE) pageNumber - 1 else null
            val nextKey = if (movies.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}
