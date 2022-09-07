package com.example.domain.movies.observers.paged

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.domain.PagingInteractor
import com.example.domain.movies.iteractors.UpdateNowPlayingMovies
import com.example.model.movie.Movie
import di.NowPlayingMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedNowPlayingMovies @Inject constructor(
    @NowPlayingMovies private val nowPlayingStore: MoviesStore,
    private val updateNowPlayingMovies: UpdateNowPlayingMovies,
) : PagingInteractor<ObservePagedNowPlayingMovies.Params, Movie>() {

    override fun createObservable(
        params: Params
    ): Flow<PagingData<Movie>> {
        return Pager(
                config = params.pagingConfig,
                remoteMediator = PaginatedMovieRemoteMediator(moviesStore = nowPlayingStore) { page ->
                    updateNowPlayingMovies.executeSync(UpdateNowPlayingMovies.Params(page))
                    pagingSourceFactory.invalidate()
                },
                pagingSourceFactory = pagingSourceFactory
            ).flow

    }

    private val pagingSourceFactory = androidx.paging.InvalidatingPagingSourceFactory {
        MoviesPagingSource(nowPlayingStore)
    }

    private fun createPagingSource(): MoviesPagingSource {
        return MoviesPagingSource(nowPlayingStore)
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<Movie>
}