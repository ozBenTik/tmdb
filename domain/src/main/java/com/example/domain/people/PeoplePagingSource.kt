package com.example.domain.people

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.people.datasource.localstore.PPeopleStore
import com.example.domain.movies.INITIAL_MOVIES_PAGE
import com.example.model.PopularActor
import timber.log.Timber
import java.io.IOException

class PPeoplePagingSource(
    val pPeopleStore: PPeopleStore
) : PagingSource<Int, PopularActor>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularActor> {

        return try {
            val pageNumber = params.key ?: INITIAL_MOVIES_PAGE

            val popularPeople = pPeopleStore.getPeopleForPage(pageNumber) ?: emptyList()

            val prevKey = if (pageNumber > INITIAL_MOVIES_PAGE) pageNumber - 1 else null
            val nextKey = if (popularPeople.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = popularPeople,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PopularActor>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}
