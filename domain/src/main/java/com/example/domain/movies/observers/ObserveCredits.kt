package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.Actor
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveCredits @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveCredits.Params, List<Actor>>() {

//    override fun createObservable(params: Params): Map<Int, List<Actor>> {
//        return moviesRepository.observeCredits()
//            .map { list ->  list.flatMap { it.value } }
//    }

    data class Params(val movieId: Int)

    override fun createObservable(params: Params): Flow<List<Actor>> {
        return moviesRepository.observeCredits()
            .map { it[params.movieId] ?: emptyList() }
    }
}