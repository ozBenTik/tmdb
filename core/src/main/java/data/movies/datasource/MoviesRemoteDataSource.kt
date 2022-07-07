package data.movies.datasource

import executeWithRetry
import network.MoviesService
import toResult
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

}