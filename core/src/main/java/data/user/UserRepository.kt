package com.example.core.data.user

import data.movies.datasource.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import result.Result
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
){

}