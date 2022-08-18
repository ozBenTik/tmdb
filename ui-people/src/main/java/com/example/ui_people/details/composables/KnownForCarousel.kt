package com.example.ui_people.details.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.model.PersonKnownFor
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbImageView

@Composable
fun KnownForCarousel(knownFor: List<PersonKnownFor>, tmdbImageUrlProvider: TmdbImageUrlProvider) {
//    val knownForRem = remember { knownFor }

    knownFor.takeIf { it.isNotEmpty() }?.run {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp)
        ) {
            items(
                count = knownFor.size,
                itemContent = { index ->
                    knownFor[index].let { movie ->
                        KnownForItem(
                            imageWidth = 450,
                            url = movie.posterPath,
                            name = movie.name ?: movie.title,
                            tmdbImageUrlProvider = tmdbImageUrlProvider
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun KnownForItem(
    imageWidth: Int,
    cardWidth: Int = 150,
    url: String?,
    name: String?,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
) {
    Card(
        Modifier.padding(all = 3.dp).width(cardWidth.dp),
        elevation = 8.dp
    ) {
        Column(

        ) {
            if (url != null)
                TmdbImageView(
                    modifier = Modifier.background(Color.Red),
                    url = tmdbImageUrlProvider.getPosterUrl(url, imageWidth),
                    contentDescription = "Known For movie ${name.takeIf { !it.isNullOrBlank() }}",
                    placeHolder = painterResource(id = R.drawable.movie_place_holder)
                )

            Text(
                text = name!!,
                modifier = Modifier.width(cardWidth.dp).padding(2.dp).height(350.dp),
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Visible,
                maxLines = 2
            )
        }
    }
}


