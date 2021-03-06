package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.model.Movie
import data.movies.MoviesStore

@OptIn(ExperimentalPagingApi::class)
class PaginatedMovieRemoteMediator(
    private val moviesStore: MoviesStore,
    private val fetch: suspend (page: Int) -> Unit
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        val nextPage = when (loadType) {
            LoadType.REFRESH -> INITIAL_MOVIES_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastPage = moviesStore.getLastPage()
                lastPage + 1
            }
        }
        return try {
            fetch(nextPage)
            MediatorResult.Success(endOfPaginationReached = false)
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }

}