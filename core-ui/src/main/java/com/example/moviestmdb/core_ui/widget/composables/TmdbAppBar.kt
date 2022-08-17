package com.example.moviestmdb.core_ui.widget.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core_ui.R

@Composable
fun TmdbAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {

    TopAppBar(
        modifier = modifier
            .safeContentPadding(),
        actions = actions,
        title = title,
        backgroundColor = Color(
            LocalContext
                .current
                .getColor(R.color.tmdb_white_50)
        ),

        elevation = 8.dp
    )

}