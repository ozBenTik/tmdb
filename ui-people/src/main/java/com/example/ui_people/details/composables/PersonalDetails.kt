package com.example.ui_people.details.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.K
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.model.PersonExtended
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbImageView

@Composable
fun PersonalDetails(details: PersonExtended, tmdbImageUrlProvider: TmdbImageUrlProvider) {
    val context: Context = LocalContext.current

    Column() {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .safeContentPadding()
        ) {
            Row(
                verticalAlignment = Alignment.Top,
            ) {
                details.popular.profilePath?.let {
                    TmdbImageView(
                        modifier = Modifier,
                        url = tmdbImageUrlProvider.getBackdropUrl(it, 220),
                        contentDescription = "${details.personal?.name ?: "Actor Name"} Image"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        details.popular.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        DetailItem(
                            "Known For", details.personal.knownForDepartment,
                            spaceBetween = 16
                        )
                        DetailItem(
                            "Known Credits", "${details.popular.knownFor.size}",
                            spaceBetween = 16
                        )
                        DetailItem(
                            "Gender", details.personal.calcGender,
                            spaceBetween = 16
                        )
                        DetailItem(
                            "Birthday", details.personal.birthday,
                            spaceBetween = 16
                        )
                        DetailItem(
                            "Death Day", details.personal.deathDay,
                            spaceBetween = 16,
                        )
                        DetailItem(
                            "Place of Birth", details.personal.placeOfBirth,
                            spaceBetween = 16,
                            maxLines = 2

                        )
                        DetailItem(
                            "Also Known As", details.personal.alsoKnownAsStrings,
                            spaceBetween = 16,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}