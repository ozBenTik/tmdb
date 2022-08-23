package com.example.domain.people.iteractors

import com.example.core.data.people.PeopleRepository
import com.example.domain.FlowInteractor
import com.example.model.person.PersonCreditsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdatePersonCredits @Inject constructor(
    private val peopleRepository: PeopleRepository,
    val dispatchers: AppCoroutineDispatchers
) : FlowInteractor<UpdatePersonDetails.Params, PersonCreditsResponse>(dispatchers.io) {

    override suspend fun doWork(params: UpdatePersonDetails.Params): Flow<Result<PersonCreditsResponse>> {
        return peopleRepository.getPersonCombinedCredits(params.personId)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> peopleRepository.savePersonCredits(
                        result.data.id,
                        result.data
                    )
                }
            }
//        return peopleRepository.getPersonTVCredits(params.personId)
//            .flatMapMerge { peopleRepository.getPersonCombinedCredits(params.personId) }
//            .onEach { result ->
//                when (result) {
//                    is Result.Error -> Timber.e(result.exception)
//                    is Result.Success -> peopleRepository.savePersonCredits(
//                        result.data.id,
//                        result.data.credits
//                    )
//                }
//            }
    }

    data class Params(val personId: Int)

}