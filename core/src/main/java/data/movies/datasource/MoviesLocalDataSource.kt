package data.movies.datasource

import com.example.model.Movie
import db.AppDatabase
import db.LocalModule
import db.TopRatedMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import util.safeApiCall
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val localModule: LocalModule
) {

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

    // TopRated with Room
    fun getTopRated(appDatabase: AppDatabase, page: Int) =
            localModule.provideTopRatedDao(appDatabase).getAll()

    suspend fun saveTopRated(appDatabase: AppDatabase, page: Int, movies: List<Movie>) =
        localModule.provideTopRatedDao(appDatabase).insertAll(TopRatedMovies(page, movies))
}