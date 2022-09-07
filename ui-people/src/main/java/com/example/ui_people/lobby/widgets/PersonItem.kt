package com.example.ui_people.lobby.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.model.person.PopularPerson
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbImageView

@Composable
fun PersonItem(
    onPersonSelected: (id: Int) -> Unit,
    person: PopularPerson,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
    widthToHeight: Pair<Int, Int>
) {

    Card(
        elevation = 8.dp,
        modifier = Modifier
            .height(widthToHeight.second.dp)
            .padding(4.dp)
            .clickable { onPersonSelected(person.id) }
    ) {

        person.profilePath?.let {
            TmdbImageView(
                modifier = Modifier.fillMaxWidth(),
                url = tmdbImageUrlProvider.getBackdropUrl(it, widthToHeight.first),
                contentDescription = "${person.name} Image",
                placeHolder = painterResource(id = R.drawable.person_place_holder)
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
                PersonName(
                    person.name!!,
                    color = Color(LocalContext.current.getColor(R.color.color_on_image_text))
                )
                PersonKnownForTitles(
                    titles = person.knownForTitles,
                    color = Color(LocalContext.current.getColor(R.color.color_on_image_text))
                )
            }
        }
    }
}

@Composable
fun PersonName(
    title: String,
    color: Color
) {
    Text(
        color = color,
        text = title,
        maxLines = 1,
        style = MaterialTheme.typography.body1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun PersonKnownForTitles(
    titles: String,
    color: Color
) {
    Text(
        color = color,
        text = titles,
        maxLines = 1,
        style = MaterialTheme.typography.body2,
        overflow = TextOverflow.Ellipsis
    )
}