package com.example.domain.users.iteractors

import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class LoginIteractor @Inject constructor(
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<LoginIteractor.Params, Boolean>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<Boolean>> {
        return userRepository.login(params.email, params.password).map {
            Result.Success(it)
        }
    }


    data class Params(val email: String, val password: String)

}