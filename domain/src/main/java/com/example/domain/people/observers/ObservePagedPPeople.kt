package com.example.domain.people.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.people.datasource.localstore.PPeopleStore
import com.example.domain.PagingInteractor
import com.example.domain.people.PPeoplePagingSource
import com.example.domain.people.PaginatedPPeopleRemoteMediator
import com.example.domain.people.iteractors.UpdatePPeople
import com.example.model.PopularActor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class ObservePagedPPeople @Inject constructor(
    private val pPeopleStore: PPeopleStore,
    private val updatePPeople: UpdatePPeople,
): PagingInteractor<ObservePagedPPeople.Params, PopularActor>() {

    @OptIn(ExperimentalPagingApi::class)
    override fun createObservable(params: Params): Flow<PagingData<PopularActor>> =
        params.language.flatMapLatest { language ->
            Pager(
                config = params.pagingConfig,
                remoteMediator = PaginatedPPeopleRemoteMediator(pPeopleStore = pPeopleStore) { page ->
                    updatePPeople.executeSync(UpdatePPeople.Params(page = page, language = language))
                    pagingSourceFactory.invalidate()
                },
                pagingSourceFactory = pagingSourceFactory
            ).flow
        }


    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): PPeoplePagingSource {
        return PPeoplePagingSource(pPeopleStore)
    }

    data class Params(
        val language: Flow<String>,
        override val pagingConfig: PagingConfig,
    ) : Parameters<PopularActor>

}