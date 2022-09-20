package com.example.ui_people.lobby.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.core_ui.R
import com.example.model.person.PopularPerson
import com.example.model.util.TmdbImageUrlProvider
import com.example.moviestmdb.core_ui.widget.composables.TmdbAppBar

@Composable
fun PeopleScreen(
    pagingData: LazyPagingItems<PopularPerson>,
    tmdbImageUrlProvider: TmdbImageUrlProvider,
    onPersonSelected: (id: Int) -> Unit,
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
                tmdbImageUrlProvider,
                onPersonSelected
            )
        },
    )
}
