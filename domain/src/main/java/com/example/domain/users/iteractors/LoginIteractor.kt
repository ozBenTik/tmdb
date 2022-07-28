package com.example.domain.users.iteractors

import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class LoginIteractor @Inject constructor(
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<LoginIteractor.Params, AuthResult>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<AuthResult>> {

        return userRepository.login(params.email,params.email).map {
            when {
                it.exception != null -> Result.Error(it.exception!!)
                else -> Result.Success(it.result)
            }
        }
    }

    data class Params(val email: String, val password: String)

}