package com.example.ui_userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.movies.iteractors.UpdateTopRatedMovies
import com.example.domain.users.iteractors.RemoveFavorite
import com.example.domain.users.observers.ObserveFavoriteMovies
import com.example.domain.users.observers.ObserveUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val observeFavoriteMovies: ObserveFavoriteMovies,
    val observeUserDetails: ObserveUserDetails,
    val removeFavoriteIteractor: RemoveFavorite,
    private val dispatchers: AppCoroutineDispatchers,
    ) : ViewModel() {

    private val favoritesLoadingState = ObservableLoadingCounter()
    private val userLoadingState = ObservableLoadingCounter()
    private val removeFavoriteState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    init {
        observeUserDetails(ObserveUserDetails.Params())
        observeFavoriteMovies(ObserveFavoriteMovies.Params())
    }

    val state: StateFlow<DetailsViewState> = combine(
        observeUserDetails.flow,
        observeFavoriteMovies.flow,
        favoritesLoadingState.observable,
        userLoadingState.observable,
        uiMessageManager.message,
    ) { userDetails, favorites, favoritesLoading, userLoading, message ->

        DetailsViewState(
            userDetails = userDetails,
            userRefreshing = userLoading,
            favorites = favorites,
            favoritesRefreshing = favoritesLoading,
            message = message
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DetailsViewState.Empty
    )


    fun removeFavorite(movieId: Int) {
        viewModelScope.launch(dispatchers.io) {
            removeFavoriteIteractor(RemoveFavorite.Params(movieId))
                .collectStatus(
                    favoritesLoadingState,
                    uiMessageManager
                )
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }
}