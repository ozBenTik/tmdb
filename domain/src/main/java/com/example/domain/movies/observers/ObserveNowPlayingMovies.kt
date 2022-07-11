package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveNowPlayingMovies @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveNowPlayingMovies.Params, List<Movie>>() {

    override fun createObservable(params: Params): Flow<List<Movie>> {
        return moviesRepository.observeNowPlayingMovies()
            .map { list ->  list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}