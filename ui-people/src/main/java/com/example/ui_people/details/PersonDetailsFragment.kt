package com.example.ui_people.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_people.details.composables.PersonScreen
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import javax.inject.Inject

@AndroidEntryPoint
class PersonDetailsFragment @Inject constructor() : Fragment() {

    private val viewModel: PersonDetailsViewModel by viewModels()

    @Inject
    lateinit var tmdbImageUrlProvider: TmdbImageUrlProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { uiState ->
                (view as ComposeView).setContent {
                    PersonScreen(
                        uiState,
                        tmdbImageUrlProvider,
                    ) {
                        viewModel.logout()
                    }
                }
            }
        }
    }
}
