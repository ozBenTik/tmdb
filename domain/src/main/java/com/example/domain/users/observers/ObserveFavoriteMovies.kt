package com.example.domain.users.observers

import com.example.core.data.user.UserRepository
import com.example.domain.SubjectInteractor
import com.example.model.movie.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveFavoriteMovies @Inject constructor(
    private val userRepository: UserRepository,
    private val moviesRepository: MoviesRepository
    ) : SubjectInteractor<ObserveFavoriteMovies.Params, List<Movie>>() {

    override fun createObservable(params: Params): Flow<List<Movie>> {
        return combine(
            userRepository.observeFavorites(),
            moviesRepository.observeNowPlayingMovies(),
            moviesRepository.observePopularMovies(),
            moviesRepository.observeUpcomingMovies(),
            moviesRepository.observeTopRatedMovies(),
        ) { favorites, nowPlaying, popular, upcoming, topRated ->
            mutableSetOf<Movie>().apply {
                addAll(nowPlaying.values.flatten())
                addAll(popular.values.flatten())
                addAll(upcoming.values.flatten())
                addAll(topRated.values.flatten())
            }.filter {
                favorites.contains(it.id)
            }
        }
    }

    data class Params(val page: Int? = null)
}