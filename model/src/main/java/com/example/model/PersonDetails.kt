package com.example.model

import com.google.gson.annotations.SerializedName

data class PersonDetails(
    /**
     * default: 0
     * minimum: 0
     * maximum: 3
     * */
    @SerializedName("gender")
    val gender: Double = 0.0,

    @SerializedName("biography")
    val biography: String = "Unknown",

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("place_of_birth")
    val placeOfBirth: String = "Unknown",

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("imdb_id")
    val imdbId: String = "",

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("birthday")
    val birthday: String = "Unknown",

    @SerializedName("known_for_department")
    val knownForDepartment: String = "Unknown",

    @SerializedName("deathday")
    val deathDay: String = "Still Alive",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "Unknown",

    @SerializedName("also_known_as")
    val alsoKnownAs: List<String> = listOf(),

    ) {

    val alsoKnownAsStrings: String
        get() = "${
            StringBuilder().apply {
                alsoKnownAs.forEach {
                    append(it)
                    append(", ")
                }
            }
        }".dropLast(1)

    val calcGender
        get() = when (gender) {
            1.0 -> "Female"
            2.0 -> "Male"
            else -> "Transsexual"
        }

    companion object {
        val Empty = PersonDetails()
    }
}