package com.example.domain.movies.usecases

import data.movies.repository.MoviesRepository
import javax.inject.Inject

class TopRatedUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
)