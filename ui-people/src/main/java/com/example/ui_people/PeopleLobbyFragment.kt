package com.example.ui_people

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core_ui.R
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_people.databinding.FragmentPeopleBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale.filter
import javax.inject.Inject

@AndroidEntryPoint
class PeopleLobbyFragment : Fragment() {

    private lateinit var binding: FragmentPeopleBinding
    private val viewModel: PeopleLobbyViewModel by viewModels()

    private lateinit var actorsAdapter: PopularActorsAdapter

    @Inject
    lateinit var tmdbImageUrlProvider: TmdbImageUrlProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        initActorsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                com.example.core_ui.R.id.signout_action_item -> {
                    viewModel.logout()
                    true
                }
                else -> {
                    false
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.pagedList.collectLatest { pagingData ->
                actorsAdapter.submitData(pagingData)
            }
        }
    }

    private fun initActorsAdapter() {
        actorsAdapter = PopularActorsAdapter(
            tmdbImageUrlProvider,
        ) {
            // To implement
        }

        binding.actorsListView.run {
            layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.numOfGridItems))
            adapter = actorsAdapter
        }
    }
}