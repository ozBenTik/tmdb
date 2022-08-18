package com.example.ui_people.details.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.model.PersonExtended
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbImageView

@Composable
fun PersonalDetails(details: PersonExtended, tmdbImageUrlProvider: TmdbImageUrlProvider) {

    Card(
        elevation = 8.dp,
        modifier = Modifier
            .height(180.dp)
            .padding(4.dp)
    ) {

        details.popular?.profilePath?.let{
            TmdbImageView(
                tmdbImageUrlProvider.getBackdropUrl(it, 180),
                "${details.personalDetails?.name ?: "Actor Name"} Image"
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .safeContentPadding()
                    .background(
                        color = Color(LocalContext.current.getColor(R.color.color_transparent_on_image_background)),
                    )
                    .height(IntrinsicSize.Min)
            ) {
//                PersonName(
//                    person.name!!,
//                    color = Color(LocalContext.current.getColor(R.color.color_on_image_text))
//                )
//                PersonKnownForTitles(
//                    titles = person.knownForTitles,
//                    color = Color(LocalContext.current.getColor(R.color.color_on_image_text))
//                )
            }
        }

    }
}