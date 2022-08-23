package com.example.model.person

import com.google.gson.annotations.SerializedName

data class PersonCrewCredit (
//
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("department")
    val department: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("overview")
    val overView: String = "Unknown",
    @SerializedName("genre_ids")
    val genreList: List<Int> = listOf(),
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val isVideo: Boolean = false,
    @SerializedName("job")
    val job: String = "Unknown",
) : PersonCredit {

    override val departmentJob: String
        get() = department ?: "Unknown"

    override val subTitleText: String
        get() = job

    override val titleText: String
        get() = title ?: originalTitle ?: "Unknown"
}