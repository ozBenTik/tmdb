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
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import javax.inject.Inject

class TvLobbyViewModel @Inject constructor(
    private val updatePopularTvShows: UpdatePopularTvShows,
    private val updateOnAirTvShows: UpdateOnAirTvShows,
    private val updateAiringTodayTvShows: UpdateAiringTodayTvShows,
    private val updateTopRatedTvShows: UpdateTopRatedTvShows,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers,
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

}