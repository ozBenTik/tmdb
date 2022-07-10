package com.example.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

// We put it at the Model package because we dont use room at our example.
// When we will use room, it should be at the db package inside the core module.

@Entity
data class MovieWithPage(

    val id: Int,

    val movieId: Int,

    @SerializedName("page")
    val page: Int,
)

