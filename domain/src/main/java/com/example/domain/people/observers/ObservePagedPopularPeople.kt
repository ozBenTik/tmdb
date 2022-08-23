package com.example.domain.people.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.people.datasource.localstore.PopularPeopleStore
import com.example.domain.PagingInteractor
import com.example.domain.people.PaginatedPopularPeopleRemoteMediator
import com.example.domain.people.PopularPeoplePagingSource
import com.example.domain.people.iteractors.UpdatePopularPeople
import com.example.model.person.PopularPerson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class ObservePagedPopularPeople @Inject constructor(
    private val popularPeopleStore: PopularPeopleStore,
    private val updatePopularPeople: UpdatePopularPeople,
): PagingInteractor<ObservePagedPopularPeople.Params, PopularPerson>() {

    @OptIn(ExperimentalPagingApi::class)
    override fun createObservable(params: Params): Flow<PagingData<PopularPerson>> =
        params.language.flatMapLatest { language ->
            Pager(
                config = params.pagingConfig,
                remoteMediator = PaginatedPopularPeopleRemoteMediator(popularPeopleStore = popularPeopleStore) { page ->
                    updatePopularPeople.executeSync(UpdatePopularPeople.Params(page = page, language = language))
                    pagingSourceFactory.invalidate()
                },
                pagingSourceFactory = pagingSourceFactory
            ).flow
        }


    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): PopularPeoplePagingSource {
        return PopularPeoplePagingSource(popularPeopleStore)
    }

    data class Params(
        val language: Flow<String>,
        override val pagingConfig: PagingConfig,
    ) : Parameters<PopularPerson>

}