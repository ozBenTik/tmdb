package com.example.ui_tvshows.lobby.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R
import com.example.model.tvshow.TvShow
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbImageView

@Composable
fun TvShowListItem(
    modifier: Modifier = Modifier,
    tvShow: TvShow,
    imageProvider: TmdbImageUrlProvider,
) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            tvShow.backdropPath?.let {
                TmdbImageView(
                    modifier = Modifier,
                    url = imageProvider.getBackdropUrl(it, 1000),
                    contentDescription = "${tvShow.name} tv show Image",
                    placeHolder = painterResource(id = R.drawable.ic_image_black_36dp)
                )
            }
            Text(
                text = tvShow.name,
                fontSize = 18.sp,
                modifier = Modifier.padding(4.dp)
            )
        }
    }

}