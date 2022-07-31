package com.example.domain.users.iteractors

import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class LogoutIteractor @Inject constructor(
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<LogoutIteractor.Params, Boolean>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<Boolean>> = flow{
        userRepository.logout()
    }

    class Params()

}