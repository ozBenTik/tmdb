package com.example.domain.movies.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.domain.PagingInteractor
import com.example.domain.movies.MoviesPagingSource
import com.example.domain.movies.PaginatedMovieRemoteMediator
import com.example.domain.movies.iteractors.UpdateDiscovery
import com.example.domain.movies.iteractors.UpdateTopRatedMovies
import com.example.model.Movie
import com.example.model.FilterParams
import di.Discovery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedDiscovery @Inject constructor(
    @Discovery private val discoveryStore: MoviesStore,
    private val updateDiscovery: UpdateDiscovery,
) : PagingInteractor<ObservePagedDiscovery.Params, Movie>() {

    override fun createObservable(
        params: Params
    ): Flow<PagingData<Movie>> {
        return params.filterParams.flatMapLatest { filterParams ->
            discoveryStore.deleteAll()
            Pager(
                config = params.pagingConfig,
                remoteMediator = PaginatedMovieRemoteMediator(moviesStore = discoveryStore) { page ->
                    updateDiscovery.executeSync(UpdateDiscovery.Params(page, filterParams))
                    pagingSourceFactory.invalidate()
                },
                pagingSourceFactory = pagingSourceFactory
            ).flow
        }
    }

    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): MoviesPagingSource {
        return MoviesPagingSource(discoveryStore)
    }

    data class Params(
        val filterParams: Flow<FilterParams>,
        override val pagingConfig: PagingConfig,
    ) : Parameters<Movie>
}