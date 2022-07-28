package upcoming

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
import com.example.ui_movies.databinding.FragmentUpcomingMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingMoviesFragment: Fragment() {

    lateinit var binding: FragmentUpcomingMoviesBinding
    private val viewModel: UpcomingMoviesViewModel by viewModels()

    lateinit var pagingAdapter: UpcomingMoviesAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingMoviesBinding.inflate(inflater)

        initAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        launchAndRepeatWithViewLifecycle {
            viewModel.pagedList.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private val movieClickListener : (Int) -> Unit = { movieId ->
        val args = Bundle().apply {
            putInt("movie_id", movieId)
        }
        findNavController().navigate(R.id.navigation_details_fragment, args)
    }

    private fun initAdapter() {
        pagingAdapter =
            UpcomingMoviesAdapter(
                tmdbImageManager.getLatestImageProvider(),
//                tmdbDateFormatter,
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