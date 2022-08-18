package com.example.moviestmdb.core_ui.widget.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.core_ui.R
import com.example.moviestmdb.core_ui.util.loadPicture

@Composable
fun TmdbImageView(
    url: String,
    contentDescription: String,
) {
    loadPicture(
        url = url,
        placeholder = painterResource(id = R.drawable.anon)
    )?.let { pointer ->
        Image(
            painter = pointer,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxWidth()
        )
    }
}