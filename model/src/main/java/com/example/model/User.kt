package com.example.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("favorite")
    val favorites: List<Int>
)