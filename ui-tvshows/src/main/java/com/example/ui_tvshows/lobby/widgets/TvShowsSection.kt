package com.example.ui_tvshows.lobby.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.tvshow.TvShow
import com.example.model.util.TmdbImageUrlProvider

@Composable
fun TvShowSection(
    title: String, tvShows: List<TvShow>,
    imageProvider: TmdbImageUrlProvider
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        LazyRow {
            items(
                items = tvShows,
                itemContent = { item ->
                    TvShowListItem(
                        tvShow = item,
                        imageProvider = imageProvider
                    )
                }
            )
        }
    }
}