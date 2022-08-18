package com.example.model

import com.google.gson.annotations.SerializedName

data class PersonCreditsResponse (
    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("cast")
    val credits: List<PersonCredits> = listOf(),
)