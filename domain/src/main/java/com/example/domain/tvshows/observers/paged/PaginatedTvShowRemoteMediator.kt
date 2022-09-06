package com.example.domain.tvshows.observers.paged

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import com.example.domain.movies.observers.paged.INITIAL_MOVIES_PAGE
import com.example.model.tvshow.TvShow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PaginatedTvShowRemoteMediator @Inject constructor(
    private val tvShowStore: TvShowsStore,
    private val fetch: suspend (page: Int) -> Unit
) : RemoteMediator<Int, TvShow>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, TvShow>): MediatorResult {
        val nextPage = when (loadType) {
            LoadType.REFRESH -> INITIAL_MOVIES_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastPage = tvShowStore.getLastPage()
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