package com.example.ui_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.movies.iteractors.UpdatePopularMovies
import com.example.domain.movies.observers.ObservePopularMovies
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
    private val dispatchers: AppCoroutineDispatchers,
    observePopularMovies: ObservePopularMovies,
) : ViewModel() {

    private val popularLoadingState = ObservableLoadingCounter()
    private val topRatedLoadingState = ObservableLoadingCounter()
    private val upcomingLoadingState = ObservableLoadingCounter()
    private val nowPlayingLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    init {
        observePopularMovies(ObservePopularMovies.Params(1))

//        viewModelScope.launch {
//            observePopularMovies.flow.collect { list ->
//                Timber.i("### ${list.size}")
//            }
//        }
    }

    val state: StateFlow<LobbyViewState> = extensions.combine(
        popularLoadingState.observable,
        topRatedLoadingState.observable,
        upcomingLoadingState.observable,
        nowPlayingLoadingState.observable,
        observePopularMovies.flow,
        uiMessageManager.message,
    ) { popularRefreshing, topRatedRefreshing, upcomingRefreshing, nowPlayingRefreshing, popularMovies, message ->

        LobbyViewState(
            popularRefreshing = popularRefreshing,
            topRatedRefreshing = topRatedRefreshing,
            upcomingRefreshing = upcomingRefreshing,
            nowPlayingRefreshing = nowPlayingRefreshing,
            popularMovies = popularMovies,
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
        }
    }
}