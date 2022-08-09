package com.example.model

import com.google.gson.annotations.SerializedName

data class TVShow(
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("overview")
    val overView: String = "",
    @SerializedName("media_type")
    val mediaType: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String> = listOf(),
    @SerializedName("genre_ids")
    val genreList: List<Int> = listOf(),
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
)