package com.example.core.data.user

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
){
    fun observeFavorites(): Flow<List<Int>> =
        userRemoteDataSource.observeFavorites()

    fun updateUserProfile(imageUrl: String?, displayName: String?) =
        userRemoteDataSource.updateUserProfile(imageUrl, displayName)

    fun addFavorite(movieId: Int) =
        userRemoteDataSource.addFavorite(movieId)

    fun removeFavorite(movieId: Int) =
        userRemoteDataSource.removeFavorite(movieId)

    fun getUserDetails() =
        userRemoteDataSource.getBasicUserInfo()

    fun login(email: String, password: String) =
        userRemoteDataSource.login(email, password)

    fun logout() =
        userRemoteDataSource.logout()

}