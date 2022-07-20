package com.example.model

import com.google.gson.annotations.SerializedName

data class Actor(

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("gender")
    val gender: Int? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("known_for_department")
    val known_for_department: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_name")
    val original_name: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("profile_path")
    val profile_path: String?,

    @SerializedName("cast_id")
    val cast_id: Int,

    @SerializedName("character")
    val character: String,

    @SerializedName("credit_id")
    val credit_id: String,

    @SerializedName("order")
    val order: Int

)