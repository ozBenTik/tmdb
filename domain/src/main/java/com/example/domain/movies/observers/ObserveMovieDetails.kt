package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveMovieDetails @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveMovieDetails.Params, Movie>() {

    override fun createObservable(params: Params): Flow<Movie> =
        combine(
            moviesRepository.observeNowPlayingMovies(),
            moviesRepository.observePopularMovies(),
            moviesRepository.observeUpcomingMovies(),
            moviesRepository.observeTopRatedMovies(),
        ) { nowPlaying, popular, upcoming, topRated ->
            mutableListOf<Movie>().apply {
                addAll(nowPlaying.values.flatten())
                addAll(popular.values.flatten())
                addAll(upcoming.values.flatten())
                addAll(topRated.values.flatten())
            }.first { it.id == params.movieId }
        }

    data class Params(val movieId: Int)
}