package com.example.domain.movies.iteractors
import com.example.domain.FlowInteractor
import com.example.model.CreditsResponse
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateCredits @Inject constructor(
    private val moviesRepository: MoviesRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateCredits.Params, CreditsResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<CreditsResponse>> {
        return moviesRepository.getCredits(params.movieId)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveCredits(
                        result.data.id,
                        result.data.actorList
                    )
                }
            }
    }

    data class Params(val movieId: Int)
}