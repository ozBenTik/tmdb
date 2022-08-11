package com.example.domain.users.iteractors

import com.example.core.data.user.UserRepository
import com.example.domain.FlowInteractor
import com.example.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateUserDetails @Inject constructor(
    private val userRepository: UserRepository,
    val dispatchers: AppCoroutineDispatchers
) : FlowInteractor<UpdateUserDetails.Params, Boolean>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<Boolean>> =
        userRepository.updateUserProfile(params.userImage, params.displayName)


    data class Params(val userImage: String?, val displayName: String? )

}