package com.example.domain.movies.iteractors
import com.example.domain.FlowInteractor
import com.example.model.MovieResponse
import data.movies.MoviesRepository
import com.example.core.data.movies.datasource.localstore.MoviesStore
import di.Popular
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdatePopularMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @Popular val popularStore: MoviesStore,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdatePopularMovies.Params, MovieResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MovieResponse>> {
        val page = when {
            params.page >= 1 -> params.page
            params.page == Page.NEXT_PAGE -> {
                val lastPage = popularStore.getLastPage()
                lastPage + 1
            }
            else -> 1
        }

        return moviesRepository.getPopularMovies(page)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.savePopularMovies(
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