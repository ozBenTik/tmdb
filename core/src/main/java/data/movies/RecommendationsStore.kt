package com.example.core.data.movies

import com.example.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationsStore @Inject constructor() {
    // Map<movieId, movies>
    private val _movieRec = MutableSharedFlow<Map<Int, List<Movie>>>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun insert(movieId: Int, movies: List<Movie>) {
        _movieRec.resetReplayCache()
        _movieRec.tryEmit(mapOf(movieId to movies))
    }

    fun observeEntries(): SharedFlow<Map<Int, List<Movie>>> = _movieRec.asSharedFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _movieRec.resetReplayCache()
    }
}
