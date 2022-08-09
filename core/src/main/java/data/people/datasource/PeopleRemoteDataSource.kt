package com.example.core.data.people.datasource

import com.example.core.network.PeopleService
import extensions.executeWithRetry
import extensions.toResult
import util.safeApiCall
import javax.inject.Inject

class PeopleRemoteDataSource @Inject constructor(
    private val peopleService: PeopleService
) {

    suspend fun getPPeople(page: Int, language: String) =
        safeApiCall {
            peopleService.getPPeople(page, language)
                .executeWithRetry()
                .toResult()
        }
}