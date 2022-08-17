package com.example.ui_people.lobby.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.example.core_ui.R
import com.example.model.PopularPerson
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbAppBar
import kotlinx.coroutines.flow.Flow

@Composable
fun PeopleScreen(
    pagingData: LazyPagingItems<PopularPerson>,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
    onToggleLogout: () -> Unit,
) {
    Scaffold(
        topBar = {
            TmdbAppBar(
                title = { Text(text = "Popular People") },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.ExitToApp,
                        tint = Color(
                            LocalContext
                                .current
                                .getColor(R.color.color_on_surface_emphasis_high)
                        ),
                        modifier = Modifier
                            .clickable(onClick = onToggleLogout)
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .height(24.dp),
                        contentDescription = "log out"
                    )
                }
            )
        },
        content = {
            PeopleGrid(
                pagingData,
                tmdbImageUrlProvider
            )
        },
    )
}


@Composable
fun PeopleGrid(lazyPeopleItems: LazyPagingItems<PopularPerson>, tmdbImageUrlProvider: TmdbImageUrlProvider) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(2.dp)
    ) {
        items(itemContent = { index ->
            lazyPeopleItems[index]?.let { person ->
                PersonItem(
                    person = person,
                    tmdbImageUrlProvider = tmdbImageUrlProvider,
                    120 to 200
                )
            }
        }, count = lazyPeopleItems.itemCount)
    }
}
