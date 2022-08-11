package data.movies.datasource

import extensions.executeWithRetry
import extensions.toResult
import network.MoviesService
import util.safeApiCall
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
) {

    suspend fun getNowPlaying(page: Int) =
        safeApiCall {
            moviesService.getNowPlaying(page)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getPopular(page: Int) =
        safeApiCall {
            moviesService.getPopular(page)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getUpcoming(page: Int) =
        safeApiCall {
            moviesService.getUpcoming(page)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getTopRated(page: Int) =
        safeApiCall {
            moviesService.getTopRated(page)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getRecommendations(movieId: Int) =
        safeApiCall {
            moviesService.getRecommended(movieId)
                .executeWithRetry()
                .toResult()
        }


    suspend fun getCredits(movieId: Int) =
        safeApiCall {
            moviesService.getCredits(movieId)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getDiscovery(page: Int, discoveryParams: Map<String, String>) =
        safeApiCall {
            moviesService.getDiscovery(page, discoveryParams)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getGenres() =
        safeApiCall {
            moviesService.getGenres()
                .executeWithRetry()
                .toResult()
        }
}