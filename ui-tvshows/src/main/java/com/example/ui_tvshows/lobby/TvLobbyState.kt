package com.example.ui_tvshows.lobby
import com.example.model.tvshow.TvShow
import util.UiMessage

data class TvLobbyState(
    val popularTvShows: List<TvShow> = emptyList(),
    val popularRefreshing: Boolean = false,
    val topRatedTvShows: List<TvShow> = emptyList(),
    val topRatedRefreshing: Boolean = false,
    val onAirTvShows: List<TvShow> = emptyList(),
    val onAirRefreshing: Boolean = false,
    val airingTodayTvShows: List<TvShow> = emptyList(),
    val airingTodayRefreshing: Boolean = false,
    val message: UiMessage? = null
) {
    val refreshing: Boolean
        get() = popularRefreshing || topRatedRefreshing || onAirRefreshing || airingTodayRefreshing

    companion object {
        val Empty = TvLobbyState()
    }
}