package nowPlaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.domain.movies.iteractors.UpdateGenres
import com.example.domain.movies.observers.ObserveGenres
import com.example.domain.movies.observers.ObservePagedNowPlayingMovies
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.MovieAndGenres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(
    observeGenres: ObserveGenres,
    private val pagedNowPlaying: ObservePagedNowPlayingMovies,
    private val logoutIteractor: LogoutIteractor,
    private val updateGenres: UpdateGenres,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private val filtered = MutableStateFlow<List<Int>>(listOf())

    val genres = observeGenres.flow

    private val filterGenres = combine(
        filtered,
        genres
    ) { filtered, genres ->
        filtered to genres
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedList: Flow<PagingData<MovieAndGenres>> =
        filterGenres.flatMapLatest { pair ->
        pagedNowPlaying.flow.map { pagingData ->
        if (pair.first.isEmpty()) {
            pagingData
        } else {
            pagingData.filter { movie ->
                movie.genreList.any { x -> x in pair.first }
            }
        }
        }.map { pagingData ->
            pagingData.map {
                MovieAndGenres(
                    movie = it,
                    genres = pair.second
                )
            }
        }
    }.cachedIn(viewModelScope)

    init {
        observeGenres(ObserveGenres.Params())
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            updateGenres(UpdateGenres.Params()).collect()
        }
        pagedNowPlaying(ObservePagedNowPlayingMovies.Params(PAGING_CONFIG))
    }

    fun filter(genreIds: List<Int>) {
        filtered.tryEmit(genreIds)
    }

    fun logout() = viewModelScope.launch(dispatchers.io) {
        logoutIteractor(LogoutIteractor.Params()).collect()
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}