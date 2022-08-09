package com.example.domain.movies.observers

import com.example.core.data.user.UserRepository
import com.example.domain.SubjectInteractor
import com.example.model.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveMovieDetails @Inject constructor(
    private val userRepository: UserRepository,
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveMovieDetails.Params, Pair<Movie, Boolean>?>() {

    override fun createObservable(params: Params): Flow<Pair<Movie, Boolean>?> =
        combine(
            userRepository.observeFavorites(),
            moviesRepository.observeNowPlayingMovies(),
            moviesRepository.observePopularMovies(),
            moviesRepository.observeUpcomingMovies(),
            moviesRepository.observeTopRatedMovies(),
        ) { favorites, nowPlaying, popular, upcoming, topRated ->
            mutableListOf<Movie>().apply {
                addAll(nowPlaying.values.flatten())
                addAll(popular.values.flatten())
                addAll(upcoming.values.flatten())
                addAll(topRated.values.flatten())
            }.firstOrNull {
                it.id == params.movieId
            }?.let {
                it to favorites.contains(it.id)
            }
        }

    data class Params(val movieId: Int)
}