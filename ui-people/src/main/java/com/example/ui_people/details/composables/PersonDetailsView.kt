package com.example.ui_people.details.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        val checkedState = remember { mutableStateOf(true) }
        PersonalDetails(uiState.details, uiState.credits.sumCredits, tmdbImageUrlProvider)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Known For",
                style = MaterialTheme.typography.h5,
                modifier = Modifier,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Details")
                Switch(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it }
                )
                Text("Titles")
            }
        }

        if (!checkedState.value) {
            KnownForTable(
                credits = uiState.credits,
            )
        } else {
            KnownForCarousel(
                credits = uiState.credits,
                tmdbImageUrlProvider = tmdbImageUrlProvider
            )
        }
    }