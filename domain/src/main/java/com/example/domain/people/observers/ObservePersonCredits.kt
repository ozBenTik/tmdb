package com.example.domain.people.observers

import com.example.core.data.people.PeopleRepository
import com.example.domain.SubjectInteractor
import com.example.model.person.PersonCreditsResponse
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ObservePersonCredits @Inject constructor(
    private val peopleRepository: PeopleRepository,
) : SubjectInteractor<ObservePersonCredits.Params, PersonCreditsResponse>() {

    override fun createObservable(params: Params): Flow<PersonCreditsResponse> =
        peopleRepository.observePersonCredits()
            .map { it[params.personId] ?: PersonCreditsResponse.Empty }
    
    data class Params(val personId: Int)
}