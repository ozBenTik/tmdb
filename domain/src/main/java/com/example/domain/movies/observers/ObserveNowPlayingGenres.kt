package com.example.domain.movies.observers

import com.example.domain.SubjectInteractor
import com.example.model.Genre
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNowPlayingGenres @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : SubjectInteractor<ObserveNowPlayingGenres.Params, List<Genre>>() {

    override fun createObservable(params: Params): Flow<List<Genre>> =
        moviesRepository.observeGenres()

    class Params()

}