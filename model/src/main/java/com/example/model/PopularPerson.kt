package com.example.model

import com.google.gson.annotations.SerializedName

data class PopularPerson(

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("known_for")
    val knownFor: List<PersonCredits> = listOf()

) {
    companion object {
        val Empty = PopularPerson()
    }

    val knownForTitles: String
        get() = "${
            StringBuilder().apply {
                knownFor.forEach {
                    append(it.name ?: it.title ?: "")
                    append(", ")
                }
            }
        }".dropLast(1)

}
