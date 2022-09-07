package com.example.domain.tvshows.interactors

import com.example.core.data.tvshows.TvShowsRepository
import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import com.example.domain.FlowInteractor
import com.example.domain.movies.iteractors.UpdateNowPlayingMovies
import com.example.model.tvshow.TvShowResponse
import di.OnAirTvShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateOnAirTvShows@Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    @OnAirTvShows val onAirTvShowsStore: TvShowsStore,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateOnAirTvShows.Params, TvShowResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<TvShowResponse>> {

        val page = when {
            params.page >= 1 -> params.page
            params.page == UpdateNowPlayingMovies.Page.NEXT_PAGE -> {
                val lastPage = onAirTvShowsStore.getLastPage()
                lastPage + 1
            }
            else -> 1
        }

        return tvShowsRepository.getOnAir(page).onEach { result ->
            when (result) {
                is Result.Error -> Timber.e(result.exception)
                is Result.Success -> tvShowsRepository.saveOnAir(
                    result.data.page,
                    result.data.tvShowsList
                )
            }
        }
    }

    data class Params(
        val page: Int
    )


    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }

}