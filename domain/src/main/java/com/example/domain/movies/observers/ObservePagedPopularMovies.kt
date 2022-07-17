package com.example.domain.movies.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.domain.InvalidatingPagingSourceFactory
import com.example.domain.MoviesPagingSource
import com.example.domain.PaginatedMovieRemoteMediator
import com.example.domain.PagingInteractor
import com.example.domain.movies.iteractors.UpdatePopularMovies
import com.example.model.Movie
import data.movies.MoviesStore
import di.Popular
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedPopularMovies @Inject constructor(
    @Popular private val popularStore: MoviesStore,
    private val updatePopularMovies: UpdatePopularMovies,
) : PagingInteractor<ObservePagedPopularMovies.Params, Movie>() {

    override fun createObservable(
        params: Params
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedMovieRemoteMediator(moviesStore = popularStore) { page ->
                updatePopularMovies.executeSync(UpdatePopularMovies.Params(page))
                pagingSourceFactory.invalidate()
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): MoviesPagingSource {
        return MoviesPagingSource(popularStore)
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<Movie>
}