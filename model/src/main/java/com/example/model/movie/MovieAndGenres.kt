package com.example.model.movie

import com.example.model.Genre

data class MovieAndGenres(
    val movie: Movie,
    val genres: List<Genre>
)