package com.example.model.movie

import com.example.model.person.PersonMovieCast
import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val actorList: List<PersonMovieCast>
)