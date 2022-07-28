package com.example.domain.users.iteractors

import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class SignoutIteractor @Inject constructor(
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<SignoutIteractor.Params, Unit>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<Unit>> {
        return userRepository.logout().map {
            Result.Success(it)
        }
    }

    data class Params(val a: Unit? = null)

}