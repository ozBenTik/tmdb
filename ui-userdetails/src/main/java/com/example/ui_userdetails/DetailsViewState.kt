package com.example.ui_userdetails
import com.example.core.data.user.AuthenticatedUserInfoBasic
import com.example.model.Movie
import util.UiMessage

data class UserDetailsViewState(
    val userDetails: AuthenticatedUserInfoBasic? = null,
    val userRefreshing: Boolean = false,
    val favorites: List<Movie> = emptyList(),
    val favoritesRefreshing: Boolean = false,
    val message: UiMessage? = null
) {
    val refreshing: Boolean
        get() = favoritesRefreshing

    companion object {
        val Empty = UserDetailsViewState()
    }
}