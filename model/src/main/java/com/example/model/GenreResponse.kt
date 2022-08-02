package com.example.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genreList: List<Genre> = listOf(),
)
