package data.movies
import com.example.model.Movie
import data.movies.datasource.MoviesLocalDataSource
import data.movies.datasource.MoviesRemoteDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remote: MoviesRemoteDataSource,
    private val local: MoviesLocalDataSource
) {

    suspend fun getPopularMovies(page: Int) =
        flow {
            emit(remote.getPopular(page))
        }

    suspend fun savePopularMovies(page: Int, movies: List<Movie>) {
        local.popularStore.insert(page, movies)
    }

    fun observePopularMovies() = local.popularStore.observeEntries()

    suspend fun getNowPlayingMovies(page: Int) =
        flow {
            emit(remote.getNowPlaying(page))
        }

    fun observeNowPlayingMovies() = local.nowPlayingStore.observeEntries()

    suspend fun getTopRatedMovies(page: Int) =
        flow {
            emit(remote.getTopRated(page))
        }

    fun observeTopRatedMovies() = local.topRatedStore.observeEntries()

    suspend fun getUpcomingMovies(page: Int) =
        flow {
            emit(remote.getUpcoming(page))
        }

    fun observeUpcomingMovies() = local.upcomingStore.observeEntries()
}