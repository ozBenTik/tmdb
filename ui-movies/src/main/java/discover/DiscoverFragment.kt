package discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.TmdbImageManager
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.moviestmdb.core_ui.widget.filterbottomshit.FiltersBottomShit
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FragmentDiscoverBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.filter -> {
            showBottomShit()
            true
        }
        R.id.logout -> {
            viewModel.logout()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }

    }

    fun showBottomShit() {
        val modalBottomSheet = FiltersBottomShit()
        modalBottomSheet.show(parentFragmentManager, FiltersBottomShit.TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.fetchDiscover(DiscoveryParams(language = "he-il"))


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

            val spacing = resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            val decoration = SpaceItemDecoration(
                spacing, -spacing
            )
            addItemDecoration(decoration)
        }
    }
}