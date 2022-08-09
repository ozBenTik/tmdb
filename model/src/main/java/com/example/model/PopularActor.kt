package com.example.model

import com.google.gson.annotations.SerializedName

data class PopularActor(
    @SerializedName("profile_path")
    val profilePath: String? = null,
    @SerializedName("adult")
    val isAdult : Boolean = false,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("known_for")
    val knownForMovies: List<Movie> = listOf(),
    @SerializedName("known_for")
    val knownForTvShows: List<TVShow> = listOf()
)