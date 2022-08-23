package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.movie.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveRecommendations @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveRecommendations.Params, List<Movie>>() {

    override fun createObservable(params: Params): Flow<List<Movie>> {
        return moviesRepository.observeRecommendations()
            .map { it[params.movieId] ?: emptyList() }
    }

    data class Params(val movieId: Int)
}