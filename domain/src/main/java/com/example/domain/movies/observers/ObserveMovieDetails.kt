package com.example.domain.movies.observers

import com.example.core.data.movies.MoviesCatchSource
import com.example.domain.SubjectInteractor
import com.example.model.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMovieDetails @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveMovieDetails.Params, Movie>() {

    override fun createObservable(params: Params): Flow<Movie> {
        return moviesRepository.getMovieCatchForSource(params.source)
            .map { list ->  list.flatMap { it.value }.first { it.id == params.movieId } }

    }

    data class Params(val movieId: Int, val source: MoviesCatchSource)
}