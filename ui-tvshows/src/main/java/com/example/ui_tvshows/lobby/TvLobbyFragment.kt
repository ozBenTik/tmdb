package com.example.ui_tvshows.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_tvshows.lobby.widgets.TvShowsLobbyScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import javax.inject.Inject

@AndroidEntryPoint
class TvLobbyFragment : Fragment() {

    private val viewModel: TvLobbyViewModel by viewModels()

    @Inject
    lateinit var tmdbImageUrlProvider: TmdbImageUrlProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { uiState ->
                (view as ComposeView).setContent {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(uiState.refreshing),
                        onRefresh = { viewModel.refresh() },
                    ) {
                        TvShowsLobbyScreen(
                            uiState = uiState,
                            tmdbImageUrlProvider = tmdbImageUrlProvider,
                            onTvShowSelected = {}
                        ) {
                            viewModel.logout()
                        }
                    }
                }
            }
        }
    }
}