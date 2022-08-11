package nowPlaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.TmdbImageManager
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FragmentNowplayingBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class NowPlayingMoviesFragment : Fragment() {

    lateinit var binding: FragmentNowplayingBinding
    private val viewModel: NowPlayingMoviesViewModel by viewModels()

    lateinit var pagingAdapter: NowPlayingMoviesAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowplayingBinding.inflate(inflater)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                com.example.core_ui.R.id.signout_action_item -> {
                    viewModel.logout()
                    true
                }
                else -> false
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.loadData()
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.genres.collect { genres ->
                genres.map { genre ->
                    genre.name?.takeIf { genre.id != null }?.let { genreName ->
                        binding.genresChipGroup.addGenre(genreName, genre.id!!) {
                            viewModel.filter(binding.genresChipGroup.chipsContainer.checkedChipIds)
                        }
                    }
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.loadData()
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.genres.collect { genres ->
                genres.map { genre ->
                    genre.name?.takeIf { genre.id != null }?.let { genreName ->
                        binding.genresChipGroup.addGenre(genreName, genre.id!!) {
                            viewModel.filter(binding.genresChipGroup.chipsContainer.checkedChipIds)
                        }
                    }
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

    private fun initAdapter() {
        pagingAdapter =
            NowPlayingMoviesAdapter(
                tmdbImageManager.getLatestImageProvider(),
                movieClickListener
            )

        binding.list.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = pagingAdapter

            val spacing = resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            val decoration = SpaceItemDecoration(
                spacing, -spacing
            )
            addItemDecoration(decoration)
        }
    }
}