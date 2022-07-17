package com.example.core.network

import com.example.model.util.Configuration
import retrofit2.Call
import retrofit2.http.GET

interface ConfigurationService {

    @GET("configuration")
    fun getConfiguration(): Call<Configuration>
}