package com.example.ui_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.movies.iteractors.UpdateNowPlayingMovies
import com.example.domain.movies.iteractors.UpdatePopularMovies
import com.example.domain.movies.iteractors.UpdateTopRatedMovies
import com.example.domain.movies.iteractors.UpdateUpcomingMovies
import com.example.domain.movies.observers.ObserveNowPlayingMovies
import com.example.domain.movies.observers.ObservePopularMovies
import com.example.domain.movies.observers.ObserveTopRatedMovies
import com.example.domain.movies.observers.ObserveUpcomingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import javax.inject.Inject


@HiltViewModel
class MoviesLobbyViewModel @Inject constructor(
    private val updatePopularMovies: UpdatePopularMovies,
    private val updateNowPlayingMovies: UpdateNowPlayingMovies,
    private val updateTopRatedMovies: UpdateTopRatedMovies,
    private val updateUpcomingMovies: UpdateUpcomingMovies,
    private val dispatchers: AppCoroutineDispatchers,
    observePopularMovies: ObservePopularMovies,
    observeTopRatedMovies: ObserveTopRatedMovies,
    observeNowPlayingMovies: ObserveNowPlayingMovies,
    observeUpcomingMovies: ObserveUpcomingMovies
) : ViewModel() {

    private val popularLoadingState = ObservableLoadingCounter()
    private val topRatedLoadingState = ObservableLoadingCounter()
    private val upcomingLoadingState = ObservableLoadingCounter()
    private val nowPlayingLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

//    init {
//        observePopularMovies(ObservePopularMovies.Params(1))
//        observeNowPlayingMovies(ObserveNowPlayingMovies.Params(1))
//        observeUpcomingMovies(ObserveUpcomingMovies.Params(1))
//        observeTopRatedMovies(ObserveTopRatedMovies.Params(1))
//    }

    val state: StateFlow<LobbyViewState> = extensions.combine(
        popularLoadingState.observable,
        topRatedLoadingState.observable,
        upcomingLoadingState.observable,
        nowPlayingLoadingState.observable,
        observePopularMovies.flow,
        observeUpcomingMovies.flow,
        observeNowPlayingMovies.flow,
        observeTopRatedMovies.flow,
        uiMessageManager.message,
    ) { popularRefreshing, topRatedRefreshing, upcomingRefreshing, nowPlayingRefreshing, popularMovies, upcoming, nowPlaying, topRated, message ->

        LobbyViewState(
            popularRefreshing = popularRefreshing,
            topRatedRefreshing = topRatedRefreshing,
            upcomingRefreshing = upcomingRefreshing,
            nowPlayingRefreshing = nowPlayingRefreshing,
            popularMovies = popularMovies,
            upcomingMovies = upcoming,
            nowPlayingMovies = nowPlaying,
            topRatedMovies = topRated,
            message = message
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LobbyViewState.Empty
    )


    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            updatePopularMovies(UpdatePopularMovies.Params(1)).collectStatus(
                popularLoadingState,
                uiMessageManager
            )
            updateNowPlayingMovies(UpdateNowPlayingMovies.Params(1)).collectStatus(
                popularLoadingState,
                uiMessageManager
            )
            updateUpcomingMovies(UpdateUpcomingMovies.Params(1)).collectStatus(
                popularLoadingState,
                uiMessageManager
            )
            updateTopRatedMovies(UpdateTopRatedMovies.Params(1)).collectStatus(
                popularLoadingState,
                uiMessageManager
            )
        }
    }
}