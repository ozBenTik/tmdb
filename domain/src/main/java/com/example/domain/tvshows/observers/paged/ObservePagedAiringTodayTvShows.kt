package com.example.domain.tvshows.observers.paged

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import com.example.domain.PagingInteractor
import com.example.domain.tvshows.interactors.UpdateAiringTodayTvShows
import com.example.model.tvshow.TvShow
import di.AiringTodayTvShows
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePagedAiringTodayTvShows @Inject constructor(
    @AiringTodayTvShows private val airingTodayStore: TvShowsStore,
    private val updateAiringTodayTvShows: UpdateAiringTodayTvShows,
): PagingInteractor<ObservePagedAiringTodayTvShows.Params, TvShow>() {

    @OptIn(ExperimentalPagingApi::class)
    override fun createObservable(params: Params): Flow<PagingData<TvShow>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedTvShowRemoteMediator(tvShowStore = airingTodayStore) { page ->
                updateAiringTodayTvShows.executeSync(UpdateAiringTodayTvShows.Params(page))
                pagingSourceFactory.invalidate()
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): TvShowsPagingSource {
        return TvShowsPagingSource(airingTodayStore)
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<TvShow>
}