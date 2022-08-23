package com.example.model.person

import com.google.gson.annotations.SerializedName

data class PopularPeopleResponse(
    @SerializedName("page")
    val page : Int = 0,
    @SerializedName("results")
    val popularPeople: List<PopularPerson> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)