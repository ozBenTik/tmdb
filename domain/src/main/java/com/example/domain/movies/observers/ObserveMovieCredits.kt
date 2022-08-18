package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.PersonMoviePart
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMovieCredits @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveMovieCredits.Params, List<PersonMoviePart>>() {

    override fun createObservable(params: Params): Flow<List<PersonMoviePart>> {
        return moviesRepository.observeCredits()
            .map { it[params.movieId] ?: emptyList() }
    }

    data class Params(val movieId: Int)

}