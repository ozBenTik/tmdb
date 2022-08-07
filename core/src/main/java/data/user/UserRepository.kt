package com.example.core.data.user

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
){
    fun observeFavorites(): Flow<List<Int>> =
        userRemoteDataSource.observeFavorites()

    fun updateImageUrl(imageUrl: String) =
        userRemoteDataSource.updateImageUrl(imageUrl)

    fun updateDisplayName(displayName: String) =
        userRemoteDataSource.updateDisplayName(displayName)

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