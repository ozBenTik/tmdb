package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.ActorPart
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveCredits @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveCredits.Params, List<ActorPart>>() {

    override fun createObservable(params: Params): Flow<List<ActorPart>> {
        return moviesRepository.observeCredits()
            .map { it[params.movieId] ?: emptyList() }
    }

    data class Params(val movieId: Int)

}