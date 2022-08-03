package data.movies

import com.example.model.Actor
import com.example.model.Movie
import data.movies.datasource.MoviesLocalDataSource
import data.movies.datasource.MoviesRemoteDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remote: MoviesRemoteDataSource,
    private val local: MoviesLocalDataSource
) {

    // ------------- Popular capabilities -------------------
    suspend fun savePopularMovies(page: Int, movies: List<Movie>) {
        local.popularStore.insert(page, movies)
    }

    suspend fun getPopularMovies(page: Int) =
        flow {
            emit(remote.getPopular(page))
        }

    fun observePopularMovies() = local.popularStore.observeEntries()

    // ------------- Now Playing capabilities -------------------
    suspend fun saveNowPlayingMovies(page: Int, movies: List<Movie>) {
        local.nowPlayingStore.insert(page, movies)
    }

    suspend fun getNowPlayingMovies(page: Int) =
        flow {
            emit(remote.getNowPlaying(page))
        }

    fun observeNowPlayingMovies() = local.nowPlayingStore.observeEntries()


    // ------------- Top Rated capabilities -------------------
    suspend fun saveTopRatedMovies(page: Int, movies: List<Movie>) {
        local.topRatedStore.insert(page, movies)
    }

    suspend fun getTopRatedMovies(page: Int) =
        flow {
            emit(remote.getTopRated(page))
        }

    fun observeTopRatedMovies() = local.topRatedStore.observeEntries()


    // ------------- Upcoming capabilities -------------------
    suspend fun saveUpcomingMovies(page: Int, movies: List<Movie>) {
        local.upcomingStore.insert(page, movies)
    }

    suspend fun getUpcomingMovies(page: Int) =
        flow {
            emit(remote.getUpcoming(page))
        }

    fun observeUpcomingMovies() = local.upcomingStore.observeEntries()

    // ------------- Recommendations capabilities -------------------
    suspend fun saveRecommendations(page: Int, movies: List<Movie>) {
        local.recommendationsStore.insert(page, movies)
    }

    suspend fun getRecommendations(movieId: Int) =
        flow {
            emit(remote.getRecommendations(movieId))
        }

    fun observeRecommendations() = local.recommendationsStore.observeEntries()

    // ------------- Credits capabilities -------------------
    suspend fun saveCredits(movieId: Int, actors: List<Actor>) {
        local.creditsStore.insert(movieId, actors)
    }

    suspend fun getCredits(movieId: Int) =
        flow {
            emit(remote.getCredits(movieId))
        }

    fun observeCredits() = local.creditsStore.observeEntries()

    // ------------- Discover capabilities -------------------
    suspend fun saveDiscovery(page: Int, movies: List<Movie>) {
        local.discoveryStore.insert(page, movies)
    }

    fun observeDiscovery() = local.discoveryStore.observeEntries()

    suspend fun getDiscovery(page: Int, discoveryParams: Map<String, String>) =
        flow {
            emit(remote.getDiscovery(page, discoveryParams))
        }
}
