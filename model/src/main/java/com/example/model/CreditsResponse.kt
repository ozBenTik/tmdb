package com.example.model

import com.google.gson.annotations.SerializedName

data class CreditsResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val actorList: List<PersonPart>
)