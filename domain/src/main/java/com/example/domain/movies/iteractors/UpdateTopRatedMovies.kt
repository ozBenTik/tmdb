package com.example.domain.movies.iteractors
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.domain.FlowInteractor
import com.example.model.movie.MovieResponse
import data.movies.MoviesRepository
import di.TopRatedMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateTopRatedMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @TopRatedMovies val topRatedStore: MoviesStore,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateTopRatedMovies.Params, MovieResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MovieResponse>> {
        val page = when {
            params.page >= 1 -> params.page
            params.page == Page.NEXT_PAGE -> {
                val lastPage = topRatedStore.getLastPage()
                lastPage + 1
            }
            else -> 1
        }

        return moviesRepository.getTopRatedMovies(page)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveTopRatedMovies(
                        result.data.page,
                        result.data.movieList
                    )
                }
            }
    }

    data class Params(val page: Int)

    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }
}