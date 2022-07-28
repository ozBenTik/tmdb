package lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.movies.iteractors.UpdateNowPlayingMovies
import com.example.domain.movies.iteractors.UpdatePopularMovies
import com.example.domain.movies.iteractors.UpdateTopRatedMovies
import com.example.domain.movies.iteractors.UpdateUpcomingMovies
import com.example.domain.movies.observers.ObserveNowPlayingMovies
import com.example.domain.movies.observers.ObservePopularMovies
import com.example.domain.movies.observers.ObserveTopRatedMovies
import com.example.domain.movies.observers.ObserveUpcomingMovies
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
class MoviesLobbyViewModel @Inject constructor(
    private val updatePopularMovies: UpdatePopularMovies,
    private val updateNowPlayingMovies: UpdateNowPlayingMovies,
    private val updateTopRatedMovies: UpdateTopRatedMovies,
    private val updateUpcomingMovies: UpdateUpcomingMovies,
    private val signoutIteractor: SignoutIteractor,
    private val dispatchers: AppCoroutineDispatchers,
    observePopularMovies: ObservePopularMovies,
    observeTopRatedMovies: ObserveTopRatedMovies,
    observeNowPlayingMovies: ObserveNowPlayingMovies,
    observeUpcomingMovies: ObserveUpcomingMovies
) : ViewModel() {

    private val popularLoadingState = ObservableLoadingCounter()
    private val topRatedLoadingState = ObservableLoadingCounter()
    private val upcomingLoadingState = ObservableLoadingCounter()
    private val nowPlayingLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    init {
        observePopularMovies(ObservePopularMovies.Params(1))
        observeUpcomingMovies(ObserveUpcomingMovies.Params(1))
        observeNowPlayingMovies(ObserveNowPlayingMovies.Params(1))
        observeTopRatedMovies(ObserveTopRatedMovies.Params(1))
        observeTopRatedMovies(ObserveTopRatedMovies.Params(1))

        refresh()
    }

    val state: StateFlow<LobbyViewState> = extensions.combine(
        popularLoadingState.observable,
        topRatedLoadingState.observable,
        upcomingLoadingState.observable,
        nowPlayingLoadingState.observable,
        observePopularMovies.flow,
        observeNowPlayingMovies.flow,
        observeTopRatedMovies.flow,
        observeUpcomingMovies.flow,
        uiMessageManager.message,
    ) { popularRefreshing, topRatedRefreshing, upcomingRefreshing, nowPlayingRefreshing, popularMovies, nowPlaying, topRated, upcoming, message ->

        LobbyViewState(
            popularRefreshing = popularRefreshing,
            topRatedRefreshing = topRatedRefreshing,
            upcomingRefreshing = upcomingRefreshing,
            nowPlayingRefreshing = nowPlayingRefreshing,
            popularMovies = popularMovies,
            topRatedMovies = topRated,
            upcomingMovies = upcoming,
            nowPlayingMovies = nowPlaying,
            message = message
        )

    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        LobbyViewState.Empty
    )

    fun signOut() {
        viewModelScope.launch(dispatchers.io) {
            signoutIteractor(SignoutIteractor.Params()).collect()
        }
    }

    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            updatePopularMovies(UpdatePopularMovies.Params(UpdatePopularMovies.Page.REFRESH))
                .collectStatus(
                    popularLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateUpcomingMovies(UpdateUpcomingMovies.Params(UpdateUpcomingMovies.Page.REFRESH))
                .collectStatus(
                    upcomingLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateNowPlayingMovies(UpdateNowPlayingMovies.Params(UpdateNowPlayingMovies.Page.REFRESH))
                .collectStatus(
                    nowPlayingLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateTopRatedMovies(UpdateTopRatedMovies.Params(UpdateTopRatedMovies.Page.REFRESH))
                .collectStatus(
                    topRatedLoadingState,
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