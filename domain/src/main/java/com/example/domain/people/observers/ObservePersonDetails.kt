package com.example.domain.people.observers

import com.example.core.data.people.PeopleRepository
import com.example.domain.SubjectInteractor
import com.example.model.Person
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePersonDetails @Inject constructor(
    private val peopleRepository: PeopleRepository,
) : SubjectInteractor<ObservePersonDetails.Params, Map<Int, Person>>() {

    override fun createObservable(params: Params): Flow<Map<Int, Person>> =
        peopleRepository.observePersonDetails()

    data class Params(val personId: Int)
}