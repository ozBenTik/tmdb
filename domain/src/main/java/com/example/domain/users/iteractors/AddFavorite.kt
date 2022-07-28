package com.example.domain.users.iteractors
import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import com.example.model.Movie
import data.movies.MoviesStore
import di.Favorite
import kotlinx.coroutines.flow.Flow
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class AddFavorite @Inject constructor(
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<AddFavorite.Params, Boolean>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<Boolean>> {
        return userRepository.addFavorite(params.movieId)
    }

    data class Params(val movieId: Int)
}