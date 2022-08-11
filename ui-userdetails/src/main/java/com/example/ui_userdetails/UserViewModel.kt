package com.example.ui_userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.domain.users.iteractors.RemoveFavorite
import com.example.domain.users.iteractors.UpdateUserDetails
import com.example.domain.users.observers.ObserveFavoriteMovies
import com.example.domain.users.observers.ObserveUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import result.data
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val observeFavoriteMovies: ObserveFavoriteMovies,
    val observeUserDetails: ObserveUserDetails,
    val updateUserDetails: UpdateUserDetails,
    val removeFavoriteIteractor: RemoveFavorite,
    private val logoutIteractor: LogoutIteractor,
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

    val state: StateFlow<UserDetailsViewState> = combine(
        observeUserDetails.flow,
        observeFavoriteMovies.flow,
        favoritesLoadingState.observable,
        userLoadingState.observable,
        uiMessageManager.message,
    ) { userDetails, favorites, favoritesLoading, userLoading, message ->

        UserDetailsViewState(
            userDetails = userDetails,
            userRefreshing = userLoading,
            favorites = favorites,
            favoritesRefreshing = favoritesLoading,
            message = message
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserDetailsViewState.Empty
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
        viewModelScope.launch(dispatchers.io) {
            uiMessageManager.clearMessage(id)
        }
    }

    fun logout() = viewModelScope.launch(dispatchers.io) {
        logoutIteractor(LogoutIteractor.Params())
    }

    fun updateUserProfile(
        imageUrl: String? = null,
        displayName: String? = null,
        onUpdated: (success: Boolean) -> Unit
    ) {
        viewModelScope.launch(dispatchers.io) {
            updateUserDetails(UpdateUserDetails.Params(imageUrl, displayName)).collect {
                withContext(dispatchers.main) {
                    onUpdated(it.data ?: false)
                }
            }
        }
    }
}