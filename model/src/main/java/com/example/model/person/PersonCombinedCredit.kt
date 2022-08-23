package com.example.model.person

import com.google.gson.annotations.SerializedName

data class PersonCombinedCredit(
    @SerializedName("media_type")
    val mediaType: String? = null,
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
    val overView: String = "Unknown",
    @SerializedName("genre_ids")
    val genreList: List<Int> = listOf(),
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
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
    @SerializedName("character")
    val character: String = "Unknown",
    @SerializedName("credit_id")
    val creditId: String = "Unknown",
    @SerializedName("episode_count")
    val episodeCount: Int = -1,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("order")
    val order: Int = -1,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String> = listOf(),
    @SerializedName("name")
    val name: String? = null,

    ) : PersonCredit {

    override val departmentJob: String
        get() = "Cast"

    override val subTitleText: String
        get() = StringBuilder().apply {

            when {
                episodeCount == 1 -> append("($episodeCount episode)")
                episodeCount > 1 -> append("($episodeCount episodes)")
            }

            if (episodeCount > 0 && character.isNotEmpty()) {
                append(" as ")
            }
            append(character)
        }.toString()

    override val titleText: String
        get() = title ?: originalTitle ?: name ?: "Unknown"
}
