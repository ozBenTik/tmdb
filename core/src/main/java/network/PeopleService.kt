package com.example.core.network

import com.example.model.PersonDetails
import com.example.model.PersonCreditsResponse
import com.example.model.PopularPeopleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {

    @GET("person/popular")
    fun getPopularPeople(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<PopularPeopleResponse>

    @GET("person/{person_id}")
    fun getPersonDetails(
        @Path("person_id") personId: Int
    ): Call<PersonDetails>


    @GET("person/{person_id}/movie_credits")
    fun getPersonMovieCredits(
        @Path("person_id") personId: Int
    ): Call<PersonCreditsResponse>

    @GET("person/{person_id}/tv_credits")
    fun getPersonTVCredits(
        @Path("person_id") personId: Int
    ): Call<PersonCreditsResponse>

}