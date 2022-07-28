package details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.movies.iteractors.UpdateCredits
import com.example.domain.movies.iteractors.UpdateRecommendations
import com.example.domain.movies.observers.ObserveCredits
import com.example.domain.movies.observers.ObserveMovieDetails
import com.example.domain.movies.observers.ObserveRecommendations
import com.example.domain.users.iteractors.AddFavorite
import com.example.domain.users.iteractors.RemoveFavorite
import com.example.domain.users.iteractors.SignoutIteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateRecommendations: UpdateRecommendations,
    private val updateCredits: UpdateCredits,
    private val observeMovieDetails: ObserveMovieDetails,
    private val observeCredits: ObserveCredits,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
    private val signoutIteractor: SignoutIteractor,
    private val observeRecommendations: ObserveRecommendations,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val recommendationsLoadingState = ObservableLoadingCounter()
    private val moviesLoadingState = ObservableLoadingCounter()
    private val creditsLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    val state: StateFlow<DetailsViewState> = extensions.combine(
        recommendationsLoadingState.observable,
        moviesLoadingState.observable,
        creditsLoadingState.observable,
        observeRecommendations.flow,
        observeCredits.flow,
        observeMovieDetails.flow,
        uiMessageManager.message,
    ) { recommendationsRefreshing, movieRefreshing, creditsRefreshing, recommendations, credits, movie, message ->

        DetailsViewState(
            movieDetails = movie,
            actorsRefreshing = creditsRefreshing,
            movieRefreshing = movieRefreshing,
            recommendations = recommendations,
            actors = credits,
            recommendationsRefreshing = recommendationsRefreshing,
            message = message
        )

    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DetailsViewState.Empty
    )

    val movieId = savedStateHandle.get<Int>("movie_id") ?: 0

    init {
        observeMovieDetails(ObserveMovieDetails.Params(movieId))
        observeCredits(ObserveCredits.Params(movieId))
        observeRecommendations(ObserveRecommendations.Params(movieId))
        refresh(movieId)
    }

    /**
     * A pair of movieId to MoviesCatchSource
     * When applied, the data would be loaded according to the movieId
     * TBD -> If possible, change it to a constructor parameter
     */
//    var movieId  = 0
//    set(value) {
//        field = value
//        observeMovieDetails(ObserveMovieDetails.Params(movieId))
//        refresh(movieId)
//    }

    private fun refresh(movieId: Int) {

        viewModelScope.launch(dispatchers.io) {
            updateCredits(UpdateCredits.Params(movieId))
                .collectStatus(
                    creditsLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateRecommendations(UpdateRecommendations.Params(movieId))
                .collectStatus(
                    recommendationsLoadingState,
                    uiMessageManager
                )
        }
    }

    fun signOut() {
        viewModelScope.launch(dispatchers.io) {
            signoutIteractor(SignoutIteractor.Params()).collect()
        }
    }

    fun applyFavorite(movieId: Int) {
        viewModelScope.launch(dispatchers.io) {
            addFavorite(AddFavorite.Params(movieId)).collect()
        }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch(dispatchers.io) {
            removeFavorite(RemoveFavorite.Params(movieId)).collect()
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }
}