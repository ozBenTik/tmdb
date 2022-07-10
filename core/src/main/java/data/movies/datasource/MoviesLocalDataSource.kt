package data.movies.datasource

import com.example.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
//    private val localModule: LocalModule
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

//    // The rest is implemented using Room
//    fun getTopRated(appDatabase: AppDatabase, page: Int) =
//        localModule.provideTopRatedDao(appDatabase).getAll()
//
//    suspend fun saveTopRated(appDatabase: AppDatabase, page: Int, movies: List<Movie>) {
//        if (page == 1) {
//            localModule.provideTopRatedDao(appDatabase).clearAll()
//        }
//        localModule.provideTopRatedDao(appDatabase).insertAll(TopRatedMovies(page, movies))
//    }
//
//    fun getPopular(appDatabase: AppDatabase, page: Int) =
//        localModule.providePopularDao(appDatabase).getAll()
//
//    suspend fun savePopular(appDatabase: AppDatabase, page: Int, movies: List<Movie>) {
//        if (page == 1) {
//            localModule.providePopularDao(appDatabase).clearAll()
//        }
//        localModule.providePopularDao(appDatabase).insertAll(PopularMovies(page, movies))
//    }
//
//    fun getUpcoming(appDatabase: AppDatabase, page: Int) =
//        localModule.provideUpcomingDao(appDatabase).getAll()
//
//    suspend fun saveUpcoming(appDatabase: AppDatabase, page: Int, movies: List<Movie>) {
//
//        if (page == 1) {
//            localModule.provideUpcomingDao(appDatabase).clearAll()
//        }
//        localModule.provideUpcomingDao(appDatabase).insertAll(UpcomingMovies(page, movies))
//
//    }

}