package com.example.model

import com.google.gson.annotations.SerializedName

data class PersonCredits(

    // tv / movie
    @SerializedName("media_type")
    val mediaType: String? = null,

    // Common
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
    @SerializedName("genre_ids")
    val genreList: List<Int> = listOf(),
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,

    //TV shows Only
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String> = listOf(),

    // Movies Only
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val isVideo: Boolean = false,

    ) {
    override fun toString(): String {
        return super.toString()
    }
}