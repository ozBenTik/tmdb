package com.example.domain.tvshows.observers.paged

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import com.example.domain.PagingInteractor
import com.example.domain.tvshows.interactors.UpdatePopularTvShows
import com.example.model.tvshow.TvShow
import di.Popular
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePagedPopularTvShows @Inject constructor(
    @Popular private val popularStore: TvShowsStore,
    private val updatePopularTvShows: UpdatePopularTvShows,
): PagingInteractor<ObservePagedPopularTvShows.Params, TvShow>() {

    @OptIn(ExperimentalPagingApi::class)
    override fun createObservable(params: Params): Flow<PagingData<TvShow>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedTvShowRemoteMediator(tvShowStore = popularStore) { page ->
                updatePopularTvShows.executeSync(UpdatePopularTvShows.Params(page))
                pagingSourceFactory.invalidate()
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): TvShowsPagingSource {
        return TvShowsPagingSource(popularStore)
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<TvShow>
}