package com.example.ui_people.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.model.PopularPerson
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_people.databinding.FragmentPeopleBinding
import com.example.ui_people.lobby.composables.PeopleScreen
import com.example.ui_people.lobby.composables.PersonItem
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@AndroidEntryPoint
class PeopleLobbyFragment : Fragment() {

    private lateinit var binding: FragmentPeopleBinding
    private val viewModel: PeopleLobbyViewModel by viewModels()

//    private lateinit var actorsAdapter: PopularActorsAdapter

    @Inject
    lateinit var tmdbImageUrlProvider: TmdbImageUrlProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            (view as ComposeView).setContent {
                PeopleScreen(
                    viewModel.pagedList.collectAsLazyPagingItems(),
                    tmdbImageUrlProvider,
                ) {
                    viewModel.logout()
                }
            }
        }
    }
}