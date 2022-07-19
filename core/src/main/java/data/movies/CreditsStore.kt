package com.example.core.data.movies

import com.example.model.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


class CreditsStore @Inject constructor() {
    // Map<movieId, actors>
    private val _movieActors = MutableSharedFlow<Map<Int, List<Actor>>>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun insert(movieId: Int, actors: List<Actor>) {
        _movieActors.resetReplayCache()
        _movieActors.tryEmit(mapOf(movieId to actors))
    }

    fun observeEntries(): SharedFlow<Map<Int, List<Actor>>> = _movieActors.asSharedFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _movieActors.resetReplayCache()
    }
}
