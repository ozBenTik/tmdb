package com.example.domain.people.observers

import androidx.lifecycle.Transformations.map
import com.example.core.data.people.PeopleRepository
import com.example.domain.SubjectInteractor
import com.example.model.PersonCredits
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ObservePersonCredits @Inject constructor(
    private val peopleRepository: PeopleRepository,
) : SubjectInteractor<ObservePersonCredits.Params, List<PersonCredits>>() {

    override fun createObservable(params: Params): Flow<List<PersonCredits>> =
        peopleRepository.observePersonCredits()
            .map { it[params.personId] ?: emptyList() }
    
    data class Params(val personId: Int)
}