package com.example.domain.users.observers

import com.example.core.data.user.AuthenticatedUserInfoBasic
import com.example.core.data.user.UserRepository
import com.example.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserDetails @Inject constructor(
    private val userRepository: UserRepository,
    ) : SubjectInteractor<ObserveUserDetails.Params, AuthenticatedUserInfoBasic?>() {

    override fun createObservable(params: Params): Flow<AuthenticatedUserInfoBasic?> {
        return userRepository.getUserDetails()
    }

    data class Params(val page: Int? = null)

}