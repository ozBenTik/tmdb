package com.example.ui_tvshows.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.tvshows.interactors.UpdateAiringTodayTvShows
import com.example.domain.tvshows.interactors.UpdateOnAirTvShows
import com.example.domain.tvshows.interactors.UpdatePopularTvShows
import com.example.domain.tvshows.interactors.UpdateTopRatedTvShows
import com.example.domain.tvshows.observers.ObserveAiringTodayShows
import com.example.domain.tvshows.observers.ObserveOnAirTvShows
import com.example.domain.tvshows.observers.ObservePopularTvShows
import com.example.domain.tvshows.observers.ObserveTopRatedTvShows
import com.example.domain.users.iteractors.LogoutIteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import javax.inject.Inject

@HiltViewModel
class TvLobbyViewModel @Inject constructor(
    private val updatePopularTvShows: UpdatePopularTvShows,
    private val updateOnAirTvShows: UpdateOnAirTvShows,
    private val updateAiringTodayTvShows: UpdateAiringTodayTvShows,
    private val updateTopRatedTvShows: UpdateTopRatedTvShows,
    private val dispatchers: AppCoroutineDispatchers,
    private val logoutIteractor: LogoutIteractor,
    observePopularTvShows: ObservePopularTvShows,
    observeTopRatedTvShows: ObserveTopRatedTvShows,
    observeOnAirTvShows: ObserveOnAirTvShows,
    observeAiringTodayTvShows: ObserveAiringTodayShows,
) : ViewModel() {

    private val popularLoadingState = ObservableLoadingCounter()
    private val topRatedLoadingState = ObservableLoadingCounter()
    private val airingTodayLoadingState = ObservableLoadingCounter()
    private val onAirLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    init {
        observePopularTvShows(ObservePopularTvShows.Params(1))
        observeTopRatedTvShows(ObserveTopRatedTvShows.Params(1))
        observeOnAirTvShows(ObserveOnAirTvShows.Params(1))
        observeAiringTodayTvShows(ObserveAiringTodayShows.Params(1))

        refresh()
    }

    val state: StateFlow<TvLobbyState> = extensions.combine(
        observePopularTvShows.flow,
        observeTopRatedTvShows.flow,
        observeOnAirTvShows.flow,
        observeAiringTodayTvShows.flow,
        popularLoadingState.observable,
        topRatedLoadingState.observable,
        airingTodayLoadingState.observable,
        onAirLoadingState.observable,
        uiMessageManager.message
    ) { popular, topRated, onAir, airingToday, popularLoading,
        topRatedLoading, airingTodayLoading, onAirLoading, message ->

        TvLobbyState(
            popularTvShows = popular,
            popularRefreshing = popularLoading,
            topRatedTvShows = topRated,
            topRatedRefreshing = topRatedLoading,
            onAirTvShows = onAir,
            onAirRefreshing = onAirLoading,
            airingTodayTvShows = airingToday,
            airingTodayRefreshing = airingTodayLoading,
            message = message
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TvLobbyState.Empty
    )

    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            updateOnAirTvShows(UpdateOnAirTvShows.Params(UpdateOnAirTvShows.Page.REFRESH))
                .collectStatus(
                    onAirLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateAiringTodayTvShows(UpdateAiringTodayTvShows.Params(UpdateAiringTodayTvShows.Page.REFRESH))
                .collectStatus(
                    airingTodayLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updatePopularTvShows(UpdatePopularTvShows.Params(UpdatePopularTvShows.Page.REFRESH))
                .collectStatus(
                    popularLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateTopRatedTvShows(UpdateTopRatedTvShows.Params(UpdateTopRatedTvShows.Page.REFRESH))
                .collectStatus(
                    topRatedLoadingState,
                    uiMessageManager
                )
        }
    }

    fun logout()  = viewModelScope.launch(dispatchers.io) {
        logoutIteractor(LogoutIteractor.Params()).collect()
    }

}