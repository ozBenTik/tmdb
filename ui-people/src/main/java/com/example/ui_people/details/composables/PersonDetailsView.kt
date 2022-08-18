package com.example.ui_people.details.composables

import androidx.compose.runtime.Composable
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
                PersonalDetails(uiState.details, tmdbImageUrlProvider)
            }
        }
    }
}
