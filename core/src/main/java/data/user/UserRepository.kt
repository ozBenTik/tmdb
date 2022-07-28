package com.example.core.data.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import data.movies.datasource.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import result.Result
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