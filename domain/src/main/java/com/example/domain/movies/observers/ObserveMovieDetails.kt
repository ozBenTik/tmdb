package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMovieDetails @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveMovieDetails.Params, Movie>() {

    override fun createObservable(params: Params): Flow<Movie> {
        return moviesRepository.getMovieFromCatch(params.movieId)
    }

    data class Params(val movieId: Int)
}