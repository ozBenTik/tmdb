package com.example.domain.people.observers

import com.example.core.data.people.PeopleRepository
import com.example.core.data.user.UserRepository
import com.example.domain.SubjectInteractor
import com.example.model.Movie
import com.example.model.Person
import com.example.model.PopularPerson
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.util.Collections.addAll
import javax.inject.Inject

class ObservePersonDetails @Inject constructor(
    private val peopleRepository: PeopleRepository,
) : SubjectInteractor<ObservePersonDetails.Params, Map<Int, Person>>() {

    override fun createObservable(params: Params): Flow<Map<Int, Person>> =
        peopleRepository.observePersonDetails()

    data class Params(val personId: Int)
}