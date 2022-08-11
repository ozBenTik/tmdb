package com.example.domain.movies.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.domain.PagingInteractor
import com.example.domain.movies.MoviesPagingSource
import com.example.domain.movies.PaginatedMovieRemoteMediator
import com.example.domain.movies.iteractors.UpdateTopRatedMovies
import com.example.model.Movie
import di.TopRated
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedTopRatedMovies @Inject constructor(
    @TopRated private val topRatedStore: MoviesStore,
    private val updateTopRatedMovies: UpdateTopRatedMovies,
) : PagingInteractor<ObservePagedTopRatedMovies.Params, Movie>() {

    override fun createObservable(
        params: Params
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedMovieRemoteMediator(moviesStore = topRatedStore) { page ->
                updateTopRatedMovies.executeSync(UpdateTopRatedMovies.Params(page))
                pagingSourceFactory.invalidate()
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): MoviesPagingSource {
        return MoviesPagingSource(topRatedStore)
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<Movie>
}