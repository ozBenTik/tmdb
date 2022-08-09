package com.example.core.network

import com.example.model.PPeopleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {

    @GET("person/popular")
    fun getPPeople(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<PPeopleResponse>

}