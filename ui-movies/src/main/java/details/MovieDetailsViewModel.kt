package details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.movies.MoviesCatchSource
import com.example.domain.movies.iteractors.UpdateCredits
import com.example.domain.movies.iteractors.UpdatePopularMovies
import com.example.domain.movies.iteractors.UpdateRecommendations
import com.example.domain.movies.observers.ObserveMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val updateRecommendations: UpdateRecommendations,
    private val updateCredits: UpdateCredits,
    private val observeMovieDetails: ObserveMovieDetails,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val recommendationsLoadingState = ObservableLoadingCounter()
    private val actorsLoadingState = ObservableLoadingCounter()
    private val movieLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    val state: StateFlow<DetailsViewState> = combine(
        recommendationsLoadingState.observable,
        actorsLoadingState.observable,
        movieLoadingState.observable,
        observeMovieDetails.flow,
        uiMessageManager.message,
    ) { recommendationsRefreshing, actorsRefreshing, movieRefreshing, movie, message ->

        DetailsViewState(
            movieDetails = movie,
            movieRefreshing = movieRefreshing,
            actorsRefreshing = actorsRefreshing,
            recommendationsRefreshing = recommendationsRefreshing,
            message = message
        )

    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DetailsViewState.Empty
    )

    /**
     * A pair of movieId to MoviesCatchSource
     * When applied, the data would be loaded according to the movieId
     * TBD -> If possible, change it to a constructor parameter
     */
    var movieParams: Pair<Int, MoviesCatchSource?> = 0 to null
    set(value) {
        field = value
        movieParams.second?.let { moviesCatchSource ->
            observeMovieDetails(ObserveMovieDetails.Params(movieParams.first, moviesCatchSource))
            refresh(movieParams.first)
        }
    }

    private fun refresh(movieId: Int) {

        viewModelScope.launch(dispatchers.io) {
            updateCredits(UpdateCredits.Params(movieId))
                .collectStatus(
                    actorsLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateRecommendations(UpdateRecommendations.Params(movieId, UpdatePopularMovies.Page.REFRESH))
                .collectStatus(
                    recommendationsLoadingState,
                    uiMessageManager
                )
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }
}