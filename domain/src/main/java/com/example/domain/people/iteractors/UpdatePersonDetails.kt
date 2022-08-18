package com.example.domain.people.iteractors

import com.example.core.data.people.PeopleRepository
import com.example.domain.FlowInteractor
import com.example.model.PersonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdatePersonDetails @Inject constructor(
    private val peopleRepository: PeopleRepository,
    val dispatchers: AppCoroutineDispatchers
) : FlowInteractor<UpdatePersonDetails.Params, PersonDetails>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<PersonDetails>> {
        return peopleRepository.getPersonDetails(params.personId)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> peopleRepository.savePersonDetails(
                        result.data.id,
                        result.data
                    )
                }
            }
    }

    data class Params(val personId: Int)
}