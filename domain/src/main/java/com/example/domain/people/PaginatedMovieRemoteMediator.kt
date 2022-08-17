package com.example.domain.people

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.data.people.datasource.localstore.PopularPeopleStore
import com.example.domain.movies.INITIAL_MOVIES_PAGE
import com.example.model.Person

@OptIn(ExperimentalPagingApi::class)
class PaginatedPopularPeopleRemoteMediator(
    private val popularPeopleStore: PopularPeopleStore,
    private val fetch: suspend (page: Int) -> Unit
) : RemoteMediator<Int, Person>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Person>
    ): MediatorResult {
        val nextPage = when (loadType) {
            LoadType.REFRESH -> INITIAL_MOVIES_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastPage = popularPeopleStore.getLastPage()
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