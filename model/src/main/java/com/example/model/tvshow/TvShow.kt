package com.example.model.tvshow

import com.google.gson.annotations.SerializedName

data class TvShow(
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
    val overview: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("genre_ids")
    val genreIds: List<Int> = listOf(),
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
)