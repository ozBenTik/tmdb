package com.example.ui_people.lobby.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.model.Person
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.util.loadPicture

@Composable
fun PersonItem(
    person: Person,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
    widthToHeight: Pair<Int, Int>
) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .height(widthToHeight.second.dp)
            .padding(4.dp)
    ) {
        personImage(
            tmdbImageUrlProvider,
            person.profilePath ?: "",
            widthToHeight.first
        )
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
fun personImage(
    tmdbImageUrlProvider: TmdbImageUrlProvider,
    url: String,
    width: Int
) {
    loadPicture(
        url = tmdbImageUrlProvider.getBackdropUrl(url, width),
        placeholder = painterResource(id = com.example.core_ui.R.drawable.anon)
    )?.let { pointer ->
        Image(
            painter = pointer,
            contentDescription = "person image",
            modifier = Modifier.fillMaxWidth()
        )
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