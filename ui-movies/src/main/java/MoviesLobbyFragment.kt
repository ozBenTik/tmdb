package com.example.ui_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ui_movies.databinding.FragmentLobbyBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoviesLobbyFragment: Fragment() {

    lateinit var binding: FragmentLobbyBinding
    private val viewModel: MoviesLobbyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.refresh()
        }

        lifecycleScope.launch {
            viewModel.state.collect {
                Timber.i("### ${it.popularMovies}")
            }
        }
    }
}