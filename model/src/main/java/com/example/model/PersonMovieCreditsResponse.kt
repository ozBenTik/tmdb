package com.example.model

import com.google.gson.annotations.SerializedName

data class PersonMovieCreditsResponse (
    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("cast")
    val movies: List<Movie> = listOf(),
)