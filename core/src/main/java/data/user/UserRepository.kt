package com.example.core.data.user

import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
){

    fun login(email: String, password: String) =
        userRemoteDataSource.login(email, password)

    fun logout() =
        userRemoteDataSource.logout()

}