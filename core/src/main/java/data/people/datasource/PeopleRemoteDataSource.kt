package com.example.core.data.people.datasource

import com.example.core.network.PeopleService
import extensions.executeWithRetry
import extensions.toResult
import util.safeApiCall
import javax.inject.Inject

class PeopleRemoteDataSource @Inject constructor(
    private val peopleService: PeopleService
) {

    suspend fun getPopularPeople(page: Int, language: String) =
        safeApiCall {
            peopleService.getPopularPeople(page, language)
                .executeWithRetry()
                .toResult()
        }

    suspend fun getPersonDetails(personId: Int) =
        safeApiCall {
            peopleService.getPersonDetails(personId)
                .executeWithRetry()
                .toResult()
        }
}