package com.example.ui_tvshows.lobby.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.tvshow.TvShow

@Composable
fun TvShowSection(
    title: String,
    tvShows: List<TvShow>,
    onTvShowSelected: (id: Int)-> Unit,
    provideFitImageUrl: (url: String) -> String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 8.dp)
        )
        LazyRow {
            items(
                items = tvShows,
                itemContent = { item ->
                    TvShowListItem(
                        tvShow = item,
                        provideFitImageUrl = { item.backdropPath?.run { provideFitImageUrl(it) }},
                        onTvShowSelected = { onTvShowSelected(item.id) }
                    )
                }
            )
        }
    }
}