package com.example.domain.people.observers

import com.example.core.data.people.PeopleRepository
import com.example.domain.SubjectInteractor
import com.example.model.PersonDetails
import com.example.model.PersonExtended
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObservePersonDetails @Inject constructor(
    private val peopleRepository: PeopleRepository,
) : SubjectInteractor<ObservePersonDetails.Params, PersonExtended>() {

    override fun createObservable(params: Params): Flow<PersonExtended> =
        peopleRepository.observePersonDetails().map { map ->
            PersonExtended(
                personalDetails = map.values.find { it.id == params.personId }
                    ?: PersonDetails(),
                popular = peopleRepository.getCachedPersonById(params.personId)
            )
        }

    data class Params(val personId: Int)
}