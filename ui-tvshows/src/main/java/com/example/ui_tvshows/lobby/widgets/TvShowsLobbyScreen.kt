package com.example.ui_tvshows.lobby.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbAppBar
import com.example.ui_tvshows.lobby.TvLobbyState

@Composable
fun TvShowsLobbyScreen(
    uiState: TvLobbyState,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
    onTvShowSelected: (id: Int) -> Unit,
    onToggleLogout: () -> Unit,
) {
    Scaffold(
        topBar = {
            TmdbAppBar(
                title = { Text(text = "Tv Shows") },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.ExitToApp,
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .clickable(onClick = onToggleLogout)
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .height(24.dp),
                        contentDescription = "log out"
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                TvShowSection(title = "Popular", tvShows = uiState.popularTvShows, imageProvider = tmdbImageUrlProvider)
                TvShowSection(title = "Top Rated", tvShows = uiState.topRatedTvShows, imageProvider = tmdbImageUrlProvider)
                TvShowSection(title = "Airing Today", tvShows = uiState.airingTodayTvShows, imageProvider = tmdbImageUrlProvider)
                TvShowSection(title = "On Air", tvShows = uiState.onAirTvShows, imageProvider = tmdbImageUrlProvider)
            }
        },
    )
}

