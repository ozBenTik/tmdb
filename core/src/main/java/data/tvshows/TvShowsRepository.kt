package com.example.core.data.tvshows

import com.example.core.data.tvshows.datasource.TvShowsLocalDataSource
import com.example.core.data.tvshows.datasource.TvShowsRemoteDataSource
import com.example.model.tvshow.TvShow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val remote: TvShowsRemoteDataSource,
    private val local: TvShowsLocalDataSource
) {

    // Airing today capabilities
    fun observeAiringToday() = local.airingTodayTvShows.observeEntries()

    fun getAiringToday(page: Int) =
        flow {
            emit(remote.getAiringToday(page))
        }

    fun saveAiringToday(page: Int, tvShows: List<TvShow>) {
        local.airingTodayTvShows.insert(page, tvShows)
    }

    // Popular capabilities
    fun observePopular() = local.popularTvShows.observeEntries()

    fun getPopular(page: Int) =
        flow {
            emit(remote.getPopular(page))
        }

    fun savePopular(page: Int, tvShows: List<TvShow>) {
        local.popularTvShows.insert(page, tvShows)
    }

    // TopRated capabilities
    fun observeTopRated() = local.topRatedTvShows.observeEntries()

    fun getTopRated(page: Int) =
        flow {
            emit(remote.getTopRated(page))
        }

    fun saveTopRated(page: Int, tvShows: List<TvShow>) {
        local.topRatedTvShows.insert(page, tvShows)
    }

    // OnAir capabilities
    fun observeOnAir() = local.onTheAirTvShows.observeEntries()

    fun getOnAir(page: Int) =
        flow {
            emit(remote.getOnAir(page))
        }

    fun saveOnAir(page: Int, tvShows: List<TvShow>) {
        local.onTheAirTvShows.insert(page, tvShows)
    }
}