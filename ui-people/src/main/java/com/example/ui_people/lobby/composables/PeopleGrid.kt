package com.example.ui_people.lobby.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.model.PopularPerson
import com.example.model.util.TmdbImageUrlProvider


@Composable
fun PeopleGrid(
    lazyPeopleItems: LazyPagingItems<PopularPerson>,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
    onPersonSelected: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(2.dp)
    ) {
        items(itemContent = { index ->
            lazyPeopleItems[index]?.let { person ->
                PersonItem(
                    onPersonSelected = onPersonSelected,
                    person = person,
                    tmdbImageUrlProvider = tmdbImageUrlProvider,
                    120 to 200
                )
            }
        }, count = lazyPeopleItems.itemCount)
    }
}