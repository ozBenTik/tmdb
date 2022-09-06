package com.example.model.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("results")
    val tvShowsList: List<TvShow> = listOf(),

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)