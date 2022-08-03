package com.example.domain.movies.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.domain.PagingInteractor
import com.example.domain.movies.MoviesPagingSource
import com.example.domain.movies.PaginatedMovieRemoteMediator
import com.example.domain.movies.iteractors.UpdateUpcomingMovies
import com.example.model.Movie
import com.example.core.data.movies.datasource.localstore.MoviesStore
import di.Upcoming
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedUpcomingMovies @Inject constructor(
    @Upcoming private val upcomingStore: MoviesStore,
    private val updateUpcomingMovies: UpdateUpcomingMovies,
) : PagingInteractor<ObservePagedUpcomingMovies.Params, Movie>() {

    override fun createObservable(
        params: Params
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedMovieRemoteMediator(moviesStore = upcomingStore) { page ->
                updateUpcomingMovies.executeSync(UpdateUpcomingMovies.Params(page))
                pagingSourceFactory.invalidate()
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): MoviesPagingSource {
        return MoviesPagingSource(upcomingStore).apply {

        }
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<Movie>
}