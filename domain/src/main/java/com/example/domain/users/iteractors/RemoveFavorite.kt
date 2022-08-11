package com.example.domain.users.iteractors
import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class RemoveFavorite @Inject constructor(
    private val userRepository: UserRepository,
    private val moviesRepository: MoviesRepository,
    private val dispatchers: AppCoroutineDispatchers
) : FlowInteractor<RemoveFavorite.Params, Boolean>(dispatchers.io) {

    data class Params(val movieId: Int)

    override suspend fun doWork(params: Params): Flow<Result<Boolean>> {
        return userRepository.removeFavorite(params.movieId)
    }
}