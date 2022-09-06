package com.example.core.data.tvshows.datasource

import com.example.core.network.TvShowsService
import extensions.executeWithRetry
import extensions.toResult
import util.safeApiCall
import javax.inject.Inject

class TvShowsRemoteDataSource @Inject constructor(
    private val tvShowsService: TvShowsService
) {
    suspend fun getAiringToday(page: Int) =
        safeApiCall {
            tvShowsService.getAiringToday(page)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getOnAir(page: Int) =
        safeApiCall {
            tvShowsService.getOnAir(page)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getPopular(page: Int) =
        safeApiCall {
            tvShowsService.getPopular(page)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getTopRated(page: Int) =
        safeApiCall {
            tvShowsService.getTopRated(page)
                .executeWithRetry()
                .toResult()
        }
}