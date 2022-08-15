package com.example.model

import com.google.gson.annotations.SerializedName

class ActorResponse {

    /**
     * default: 0
     * minimum: 0
     * maximum: 3
     * */
    @SerializedName("gender")
    val gender: Double = 0.0
    @SerializedName("biography")
    val biography: String = ""
    @SerializedName("popularity")
    val popularity: Double? = null
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null
    @SerializedName("profile_path")
    val profilePath: String? = null
    @SerializedName("adult")
    val adult: Boolean = false
    @SerializedName("imdb_id")
    val imdbId: String = ""
    @SerializedName("homepage")
    val homepage: String? = null
    @SerializedName("birthday")
    val birthday: String? = null
    @SerializedName("known_for_department")
    val knownForDepartment: String = ""
    @SerializedName("deathday")
    val deathDay: String? = null
    @SerializedName("id")
    val id: Int = 0
    @SerializedName("name")
    val name: String = ""
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String> = listOf()

}