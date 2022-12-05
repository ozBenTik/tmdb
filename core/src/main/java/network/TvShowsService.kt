package com.example.core.network

import com.example.model.tvshow.TvShowDetails
import com.example.model.tvshow.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowsService {

    @GET("tv/on_the_air")
    fun getOnAir(@Query("page") page: Int): Call<TvShowResponse>

    @GET("tv/popular")
    fun getPopular(@Query("page") page: Int): Call<TvShowResponse>

    @GET("tv/airing_today")
    fun getAiringToday(@Query("page") page: Int): Call<TvShowResponse>

    @GET("tv/top_rated")
    fun getTopRated(@Query("page") page: Int): Call<TvShowResponse>

    @GET("tv")
    fun getDetails(@Query("tv_id") id: Int): Call<TvShowDetails>
}