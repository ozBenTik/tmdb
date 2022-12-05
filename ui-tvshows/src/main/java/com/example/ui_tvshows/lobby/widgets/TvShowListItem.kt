package com.example.ui_tvshows.lobby.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R
import com.example.model.tvshow.TvShow
import com.example.moviestmdb.core_ui.widget.composables.TmdbImageView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TvShowListItem(
    modifier: Modifier = Modifier,
    tvShow: TvShow,
    onTvShowSelected: (id: Int) -> Unit,
    provideFitImageUrl: (imageUrl: String) -> String?,
) {
    tvShow.backdropPath?.let { imageUrl ->
        Card(
            modifier = Modifier
                .width(240.dp)
                .padding(4.dp),
            elevation = 8.dp,
            onClick = { onTvShowSelected(tvShow.id) }
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                TmdbImageView(
                    modifier = Modifier,
                    url = provideFitImageUrl(imageUrl) ?: "",
                    contentDescription = "${tvShow.name} tv show Image",
                    placeHolder = painterResource(id = R.drawable.ic_image_black_36dp)
                )
                Text(
                    style = MaterialTheme.typography.body2,
                    text = tvShow.name,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .defaultMinSize(minHeight = 50.dp)
                )
            }
        }
    }
}