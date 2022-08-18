package com.example.core.network

import com.example.model.PersonDetails
import com.example.model.PopularPeopleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {

    @GET("person/popular")
    fun getPopularPeople(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<PopularPeopleResponse>

    @GET("person/popular")
    fun getPersonDetails(
        @Query("person_id") actorId: Int
    ): Call<PersonDetails>

}