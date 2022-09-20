package com.example.ui_people.details.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DetailItem(
    key: String?,
    value: String?,
    keyStyle: TextStyle = MaterialTheme.typography.subtitle1,
    valueColorRes: TextStyle = MaterialTheme.typography.body1,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    if (key == null || value == null ) return
    LazyColumn(
        modifier = modifier
    ) {

        item {
            Row(Modifier) {
                Text(
                    modifier = modifier.width(130.dp),
                    text = key,
                    maxLines = maxLines,
                    style = keyStyle,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = value,
                    maxLines = maxLines,
                    style = valueColorRes,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}