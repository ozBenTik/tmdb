package lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.TmdbImageManager
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FragmentLobbyBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MoviesLobbyFragment: Fragment() {

    lateinit var binding: FragmentLobbyBinding
    private val viewModel: MoviesLobbyViewModel by viewModels()

    lateinit var popularMoviesAdapter: PopularMoviesCarrouselAdapter
    lateinit var topRatedMoviesAdapter: PopularMoviesCarrouselAdapter
    lateinit var upcomingMoviesAdapter: PopularMoviesCarrouselAdapter
    lateinit var nowPlayingMoviesAdapter: PopularMoviesCarrouselAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

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

        initPopularAdapter()
        initNowPlayingAdapter()
        initUpcomingAdapter()
        initTopRatedAdapter()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { uiState ->

                binding.swipeRefresh.isRefreshing = uiState.refreshing

                uiState.message?.let { message ->
                    Snackbar.make(requireView(), message.message, Snackbar.LENGTH_LONG)
                        .setAction("Dismiss") {
                            viewModel.clearMessage(message.id)
                        }
                        .show()
                }

                binding.popularMoviesView.setLoading(uiState.popularRefreshing)
                popularMoviesAdapter.submitList(uiState.popularMovies)

                binding.topRatedMoviesView.setLoading(uiState.topRatedRefreshing)
                topRatedMoviesAdapter.submitList(uiState.topRatedMovies)

                binding.nowPlayingMoviesView.setLoading(uiState.nowPlayingRefreshing)
                nowPlayingMoviesAdapter.submitList(uiState.nowPlayingMovies)

                binding.upcomingMoviesView.setLoading(uiState.upcomingRefreshing)
                upcomingMoviesAdapter.submitList(uiState.upcomingMovies)
            }
        }
    }

    private val onMovieClick: (Int) -> Unit = { movieId ->
        Timber.i("$movieId")
    }

    private fun initPopularAdapter() {
        popularMoviesAdapter = PopularMoviesCarrouselAdapter(
            tmdbImageManager.getLatestImageProvider(),
            onMovieClick,
        )
        binding.popularMoviesView.recyclerView.run {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val itemSpacing =
                resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            addItemDecoration(
                SpaceItemDecoration(
                itemSpacing, -itemSpacing
            )
            )
        }

        binding.popularMoviesView.title.text = "Popular Movies"
        binding.popularMoviesView.more.setOnClickListener {
            findNavController().navigate(R.id.navigation_popular_movies_fragment)
        }
    }

    private fun initNowPlayingAdapter() {
        nowPlayingMoviesAdapter = PopularMoviesCarrouselAdapter(
            tmdbImageManager.getLatestImageProvider(),
            onMovieClick,
        )
        binding.nowPlayingMoviesView.recyclerView.run {
            adapter = nowPlayingMoviesAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val itemSpacing =
                resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            addItemDecoration(SpaceItemDecoration(
                itemSpacing, -itemSpacing
            ))
        }

        binding.nowPlayingMoviesView.title.text = "Now Playing Movies"
        binding.nowPlayingMoviesView.more.setOnClickListener {
            findNavController().navigate(R.id.navigation_popular_movies_fragment)
        }
    }

    private fun initTopRatedAdapter() {
        topRatedMoviesAdapter = PopularMoviesCarrouselAdapter(
            tmdbImageManager.getLatestImageProvider(),
            onMovieClick,
        )
        binding.topRatedMoviesView.recyclerView.run {
            adapter = topRatedMoviesAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val itemSpacing =
                resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            addItemDecoration(SpaceItemDecoration(
                itemSpacing, -itemSpacing
            ))
        }

        binding.topRatedMoviesView.title.text = "Top Rated Movies"
        binding.topRatedMoviesView.more.setOnClickListener {
            findNavController().navigate(R.id.navigation_popular_movies_fragment)
        }
    }

    private fun initUpcomingAdapter() {
        upcomingMoviesAdapter = PopularMoviesCarrouselAdapter(
            tmdbImageManager.getLatestImageProvider(),
            onMovieClick,
        )
        binding.upcomingMoviesView.recyclerView.run {
            adapter = upcomingMoviesAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val itemSpacing =
                resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            addItemDecoration(SpaceItemDecoration(
                itemSpacing, -itemSpacing
            ))
        }

        binding.upcomingMoviesView.title.text = "Upcoming Movies"
        binding.upcomingMoviesView.more.setOnClickListener {
            findNavController().navigate(R.id.navigation_popular_movies_fragment)
        }
    }
}