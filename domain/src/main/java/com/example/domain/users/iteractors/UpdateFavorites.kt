package com.example.domain.users.iteractors
import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

//class UpdateFavorites @Inject constructor(
//    private val userRepository: UserRepository,
//    dispatchers: AppCoroutineDispatchers,
//) : FlowInteractor<UpdateFavorites.Params, Boolean>(dispatchers.io) {
//
//    override suspend fun doWork(params: Params): Flow<Result<Boolean>> {
//        return userRepository.updateFavorites()
//    }
//
//    data class Params(val movieId: Int)
//}