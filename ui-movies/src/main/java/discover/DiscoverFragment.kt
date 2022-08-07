package discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.TmdbImageManager
import com.example.model.FilterParams
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FragmentDiscoverBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import filterbottomshit.FiltersBottomShit
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    lateinit var binding: FragmentDiscoverBinding
    private val viewModel: DiscoverViewModel by viewModels()

    lateinit var pagingAdapter: DiscoverAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater)
        initAdapter()
        return binding.root
    }

    private fun showBottomShit() {

        viewModel.requireParams { filterParams ->
            filterParams.let { currentParams ->
                FiltersBottomShit(currentParams) { updatedParams ->
                    if (updatedParams != currentParams) {
                        viewModel.applyFilters(
                            FilterParams(
                                updatedParams.language,
                                updatedParams.release_dateFrom,
                                updatedParams.release_dateTo,
                                updatedParams.genres
                            )
                        )
                    }
                }.show(parentFragmentManager, FiltersBottomShit.TAG)
            }
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