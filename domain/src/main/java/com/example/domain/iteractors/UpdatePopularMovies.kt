package com.example.domain.iteractors
import com.example.domain.FlowInteractor
import com.example.model.MoviesResponse
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdatePopularMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdatePopularMovies.Params, MoviesResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MoviesResponse>> {
        return moviesRepository.getPopularMovies(params.page)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.savePopularMovies(
                        result.data.page,
                        result.data.results
                    )

                }
            }
    }

    data class Params(val page: Int)
}