package data.movies
import com.example.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class MoviesStore @Inject constructor() {
    private val _movies = MutableSharedFlow<Map<Int, List<Movie>>>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun insert(page: Int, movies: List<Movie>) {
        if (page == 1) {
            _movies.resetReplayCache()
            _movies.tryEmit(mapOf(page to movies))
        } else {
            val map = _movies.replayCache.first().toMutableMap()
            map[page] = movies
            _movies.tryEmit(map)
        }
    }
    fun observeEnteries(): SharedFlow<Map<Int, List<Movie>>> = _movies.asSharedFlow()

    fun updatePage(page: Int, movies: List<Movie>) {
        val map = _movies.replayCache.first().toMutableMap()
        map[page] = movies
        _movies.tryEmit(map)
    }

    fun deletePage(page: Int) {
        val map = _movies.replayCache.first().toMutableMap()
        map.remove(page)
        _movies.tryEmit(map)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _movies.resetReplayCache()
    }
}