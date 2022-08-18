package com.example.domain.people.observers

import com.example.core.data.people.PeopleRepository
import com.example.domain.SubjectInteractor
import com.example.model.PersonExtended
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ObservePersonDetails @Inject constructor(
    private val peopleRepository: PeopleRepository,
) : SubjectInteractor<ObservePersonDetails.Params, PersonExtended>() {

    override fun createObservable(params: Params): Flow<PersonExtended> =
        peopleRepository.observePersonDetails().map { map ->
            map[params.personId]?.let { personDetails ->
                PersonExtended(
                    personal = personDetails,
                    popular = peopleRepository.getCachedPersonById(params.personId)
                )
            } ?: PersonExtended()

        }

    data class Params(val personId: Int)
}