package discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.TmdbImageManager
import com.example.model.FilterParams
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FragmentDiscoverBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collectLatest
import utils.filterbottomshit.FiltersBottomShit
import javax.inject.Inject


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    lateinit var binding: FragmentDiscoverBinding
    private val viewModel: DiscoverViewModel by viewModels()

    private lateinit var pagingAdapter: DiscoverAdapter
    private lateinit var onSubmitFilters: (filters: FilterParams) -> Unit

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDiscoverBinding.inflate(inflater)
        initFiltersCallback()
        initAdapter()
        return binding.root
    }

    private fun showBottomShit() {
        viewModel.requireFilters { currentFilters ->
            FiltersBottomShit(currentFilters).apply {
                onSubmit = onSubmitFilters
            }.show(parentFragmentManager, FiltersBottomShit.TAG)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter -> {
                    showBottomShit()
                    true
                }
                R.id.logout -> {
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
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private val movieClickListener: (Int) -> Unit = { movieId ->

        val args = Bundle().apply {
            putInt("movie_id", movieId)
        }
        findNavController().navigate(R.id.navigation_details_fragment, args)
    }

    private fun initFiltersCallback() {
        onSubmitFilters = { updatedParams ->
            viewModel.applyFilters(updatedParams)
            pagingAdapter.refresh()
        }
    }

    private fun initAdapter() {
        pagingAdapter =
            DiscoverAdapter(
                tmdbImageManager.getLatestImageProvider(),
                movieClickListener
            )

        binding.list.run {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = pagingAdapter
        }
    }
}