package com.example.core.data.movies.datasource.localstore

import com.example.model.PersonPart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditsStore @Inject constructor() {
    // Map<movieId, actors>
    private val _movieActors = MutableSharedFlow<Map<Int, List<PersonPart>>>(replay = 1)

    fun insert(movieId: Int, actors: List<PersonPart>) {
        _movieActors.tryEmit(mapOf(movieId to actors))
    }

    fun observeEntries(): SharedFlow<Map<Int, List<PersonPart>>> = _movieActors.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _movieActors.resetReplayCache()
    }
}
