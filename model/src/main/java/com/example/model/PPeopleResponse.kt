package com.example.model

import com.google.gson.annotations.SerializedName

data class PPeopleResponse(
    @SerializedName("page")
    val page : Int = 0,
    @SerializedName("results")
    val pPeople: List<PopularActor> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)