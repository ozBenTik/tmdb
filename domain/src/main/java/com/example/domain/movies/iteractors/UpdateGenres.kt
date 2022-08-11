package com.example.domain.movies.iteractors

import com.example.domain.FlowInteractor
import com.example.model.GenreResponse
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateGenres @Inject constructor(
    private val moviesRepository: MoviesRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateGenres.Params, GenreResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<GenreResponse>> {
        return moviesRepository.getGenres()
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveGenres(
                        result.data.genreList
                    )
                }
            }
    }

    class Params()
}