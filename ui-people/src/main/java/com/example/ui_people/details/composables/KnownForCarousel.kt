package com.example.ui_people.details.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.model.PersonCredits
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbImageView

@Composable
fun KnownForCarousel(
    height: Int,
    credits: List<PersonCredits>,
    tmdbImageUrlProvider: TmdbImageUrlProvider
) {

    credits.takeIf { it.isNotEmpty() }?.run {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
        ) {
            Text(
                "Known For",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp, top = 8.dp),
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp)
            ) {
                items(
                    count = credits.size,
                    itemContent = { index ->
                        credits[index].let { credit ->
                            CreditItem(
                                imageWidth = 450,
                                url = credit.posterPath ?: credit.backdropPath,
                                name = credit.name ?: credit.title,
                                tmdbImageUrlProvider = tmdbImageUrlProvider
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CreditItem(
    imageWidth: Int,
    cardWidth: Int = 150,
    url: String?,
    name: String?,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
) {
    Card(
        Modifier
            .padding(all = 3.dp)
            .width(cardWidth.dp),
        elevation = 8.dp
    ) {
        Row(
            Modifier.height(300.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                if (url != null)
                    TmdbImageView(
                        modifier = Modifier.background(Color.Red),
                        url = tmdbImageUrlProvider.getPosterUrl(url, imageWidth),
                        contentDescription = "Known For  ${name.takeIf { !it.isNullOrBlank() }}",
                        placeHolder = painterResource(id = R.drawable.movie_place_holder)
                    )

                Text(
                    text = name!!,
                    modifier = Modifier
                        .width(cardWidth.dp)
                        .padding(2.dp)
                        .height(350.dp),
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Visible,
                    maxLines = 2
                )
            }
        }
    }
}


