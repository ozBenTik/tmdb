package com.example.ui_people.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_people.R
import com.example.ui_people.lobby.widgets.PeopleScreen
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import javax.inject.Inject

@AndroidEntryPoint
class PeopleLobbyFragment : Fragment() {

    private val viewModel: PeopleLobbyViewModel by viewModels()

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
                    { id ->
                        val args = Bundle().apply {
                            putInt("person_id", id)
                        }
                        findNavController().navigate(R.id.navigation_person_fragment, args)
                    }
                ) {
                    viewModel.logout()
                }
            }
        }
    }
}