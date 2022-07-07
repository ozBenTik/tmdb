package data.movies.datasource

import com.example.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor() {

    // NowPlaying with shared flow
    private var _nowPlaying = MutableSharedFlow<Map<Int, List<Movie>>>()
    val nowPlaying = _nowPlaying.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun saveNowPlaying(page: Int, movies: List<Movie>) = run {
        if (page == 1) {
            _nowPlaying.resetReplayCache()
        }
        _nowPlaying.tryEmit(mapOf(page to movies))
    }


}