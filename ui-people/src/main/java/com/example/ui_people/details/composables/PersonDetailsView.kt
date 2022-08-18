package com.example.ui_people.details.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbCenteredProgressBar
import com.example.moviestmdb.core_ui.widget.composables.TmdbSnackbar
import com.example.ui_people.details.PersonDetailsState

@Composable
fun PersonDetailsView(
    uiState: PersonDetailsState,
    tmdbImageUrlProvider: TmdbImageUrlProvider
) {
    uiState.let {
        when {
            it.refreshing -> TmdbCenteredProgressBar()
            it.message != null -> TmdbSnackbar(it.message.message)
            else -> {
                PageContent(uiState, tmdbImageUrlProvider)
            }
        }
    }
}

@Composable
fun PageContent(uiState: PersonDetailsState, tmdbImageUrlProvider: TmdbImageUrlProvider) =
    Column(
        Modifier.fillMaxWidth()
    ) {
        PersonalDetails(uiState.details, tmdbImageUrlProvider)
        KnownForCarousel(
            500,
            credits = uiState.credits,
            tmdbImageUrlProvider = tmdbImageUrlProvider
        )
    }