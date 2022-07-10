package com.example.ui_movies

import com.example.model.Movie
import util.UiMessage

data class LobbyViewState(
    val popularMovies: List<Movie> = emptyList(),
    val popularRefreshing: Boolean = false,
    val topRatedMovies: List<Movie> = emptyList(),
    val topRatedRefreshing: Boolean = false,
    val upcomingMovies: List<Movie> = emptyList(),
    val upcomingRefreshing: Boolean = false,
    val nowPlayingMovies: List<Movie> = emptyList(),
    val nowPlayingRefreshing: Boolean = false,
    val message: UiMessage? = null
) {
    val refreshing: Boolean
        get() = popularRefreshing || topRatedRefreshing || nowPlayingRefreshing || upcomingRefreshing

    companion object {
        val Empty = LobbyViewState()
    }
}