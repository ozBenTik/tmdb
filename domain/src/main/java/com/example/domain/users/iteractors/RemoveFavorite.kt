package com.example.domain.users.iteractors
import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import com.example.domain.SubjectInteractor
import com.example.domain.movies.observers.ObserveMovieDetails
import com.example.model.Movie
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import result.Result
import util.AppCoroutineDispatchers
import java.util.Collections.addAll
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