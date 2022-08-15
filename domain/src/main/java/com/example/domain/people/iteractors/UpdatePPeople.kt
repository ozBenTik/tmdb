package com.example.domain.people.iteractors

import com.example.core.data.people.PeopleRepository
import com.example.core.data.people.datasource.localstore.PopularPeopleStore
import com.example.domain.FlowInteractor
import com.example.model.PopularPeopleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdatePopularPeople @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val popularPeopleStore: PopularPeopleStore,
    val dispatchers: AppCoroutineDispatchers
) : FlowInteractor<UpdatePopularPeople.Params, PopularPeopleResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<PopularPeopleResponse>> {
        return (
                when {
                    params.page >= 1 -> params.page
                    params.page == Page.NEXT_PAGE -> {
                        val lastPage = popularPeopleStore.getLastPage()
                        lastPage + 1
                    }
                    else -> 1
                }.let { updatedPage ->
                    peopleRepository.getPopularPeople(updatedPage, params.language)
                        .onEach { result ->
                            when (result) {
                                is Result.Error -> Timber.e(result.exception)
                                is Result.Success -> peopleRepository.savePopularPeople(
                                    result.data.page,
                                    result.data.popularPeople
                                )
                            }
                        }
                })
    }

    class Params(val page: Int, val language: String)

    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }
}