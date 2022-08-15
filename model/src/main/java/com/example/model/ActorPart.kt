package com.example.model

import com.google.gson.annotations.SerializedName

data class ActorPart(

    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("gender")
    val gender: Int? = null,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("known_for_department")
    val department: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("original_name")
    val originalName: String? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("cast_id")
    val castId: Int? = null,

    @SerializedName("character")
    val character: String? = null,

    @SerializedName("credit_id")
    val creditId: String? = null,

    @SerializedName("order")
    val order: Int? = null

)