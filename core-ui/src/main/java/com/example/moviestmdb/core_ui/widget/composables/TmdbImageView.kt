package com.example.moviestmdb.core_ui.widget.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.core_ui.R
import com.example.moviestmdb.core_ui.util.loadPicture

@Composable
fun TmdbImageView(
    modifier: Modifier,
    url: String,
    contentDescription: String,
    placeHolder: Painter = painterResource(id = R.drawable.person_place_holder)
) {
    loadPicture(
        url = url,
        placeholder = placeHolder
    )?.let { pointer ->
        Image(
            painter = pointer,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}