package com.example.core.data.movies.datasource.localstore

import com.example.model.ActorPart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditsStore @Inject constructor() {
    // Map<movieId, actors>
    private val _movieActors = MutableSharedFlow<Map<Int, List<ActorPart>>>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun insert(movieId: Int, actors: List<ActorPart>) {
        _movieActors.tryEmit(mapOf(movieId to actors))
    }

    fun observeEntries(): SharedFlow<Map<Int, List<ActorPart>>> = _movieActors.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _movieActors.resetReplayCache()
    }
}
