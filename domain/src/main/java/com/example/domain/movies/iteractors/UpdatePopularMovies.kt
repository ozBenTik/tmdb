package com.example.domain.movies.iteractors
import com.example.domain.FlowInteractor
import com.example.model.MovieResponse
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdatePopularMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdatePopularMovies.Params, MovieResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MovieResponse>> {
        return moviesRepository.getPopularMovies(params.page)
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
}