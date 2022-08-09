package details

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
import com.bumptech.glide.Glide
import com.example.core.TmdbImageManager
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var recommendedAdapter: RecommendedMoviesCarrouselAdapter
    private lateinit var actorsAdapter: ActorsCarrouselAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecommendedAdapter()
        initActorsAdapter()

        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val totalScrollLength = binding.detailsBackdropImageView.height
            val progress: Float = scrollY.toFloat() / totalScrollLength
            binding.mainLayout.progress = progress
        }

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

            viewModel.state.collect { uiState ->

                if (uiState != DetailsViewState.Empty) {
                    binding.contentLoadingView.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE

//                Timber.i("$$$$ recs = ${uiState.recommendations}  actors = ${uiState.actors}")

                    uiState.movieDetails?.let { movieDetails ->

                        binding.favoritesSwitch.isChecked = movieDetails.second

                        movieDetails.first.let { movie ->
                            binding.favoritesSwitch.setOnCheckedChangeListener { compoundButton, b ->
                                if (b) {
                                    viewModel.applyFavorite(movie.id)
                                } else {
                                    viewModel.removeFavorite(movie.id)
                                }
                            }

                            uiState.message?.let { message ->
                                Snackbar.make(requireView(), message.message, Snackbar.LENGTH_LONG)
                                    .setAction("Dismiss") {
                                        viewModel.clearMessage(message.id)
                                    }
                                    .show()
                            }

                            binding.toolbar.title = movie.title
                            binding.title.text = movie.title
                            binding.detailsOverviewTextView.text = movie.overView
                            binding.detailsRatingPrecentageTextView.text =
                                "${movie.popularityPrecentage}%"
                            binding.detailsNumberOfVotesTextView.text =
                                "${(movie.voteCount ?: 0) / 1000}K votes"
                            binding.detailsStatusTextView.text =
                                if (LocalDate.parse(movie.releaseDate).isAfter(LocalDate.now()))
                                    "To be released at\n${movie.releaseDate}"
                                else
                                    "released"

                            movie.posterPath?.let { posterPath ->
                                Glide.with(binding.root)
                                    .load(
                                        tmdbImageManager.getLatestImageProvider().getPosterUrl(
                                            path = posterPath,
                                            imageWidth = binding.detailsPosterImageView.width
                                        )
                                    )
                                    .into(binding.detailsPosterImageView)

                            }

                            movie.backdropPath?.let { backdropPath ->
                                Glide.with(binding.root)
                                    .load(
                                        tmdbImageManager.getLatestImageProvider().getBackdropUrl(
                                            path = backdropPath,
                                            imageWidth = binding.root.width
                                        )
                                    )
                                    .into(binding.detailsBackdropImageView)

                            }
                        }

                        binding.actorsView.setLoading(uiState.actorsRefreshing)
                        actorsAdapter.submitList(uiState.actors)

                        binding.recommendedMoviesView.setLoading(uiState.recommendationsRefreshing)
                        recommendedAdapter.submitList(uiState.recommendations)

                    }
                }
            }
        }
    }


    private fun initRecommendedAdapter() {
        recommendedAdapter = RecommendedMoviesCarrouselAdapter(
            tmdbImageManager.getLatestImageProvider()
        )
        binding.recommendedMoviesView.recyclerView.run {
            adapter = recommendedAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val itemSpacing =
                resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            addItemDecoration(
                SpaceItemDecoration(
                    itemSpacing, -itemSpacing
                )
            )
        }

        binding.recommendedMoviesView.title.text = "Recommended Movies"
    }

    private fun initActorsAdapter() {
        actorsAdapter = ActorsCarrouselAdapter(
            tmdbImageManager.getLatestImageProvider()
        )

        binding.actorsView.recyclerView.run {
            adapter = actorsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val itemSpacing =
                resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            addItemDecoration(
                SpaceItemDecoration(
                    itemSpacing, -itemSpacing
                )
            )
        }

        binding.actorsView.title.text = "Top Billed Cast"
    }

}