package com.example.core.data.movies.datasource.localstore

import com.example.model.person.PersonMovieCast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditsStore @Inject constructor() {
    // Map<movieId, actors>
    private val _movieActors = MutableSharedFlow<Map<Int, List<PersonMovieCast>>>(replay = 1)

    fun insert(movieId: Int, actors: List<PersonMovieCast>) {
        _movieActors.tryEmit(mapOf(movieId to actors))
    }

    fun observeEntries(): SharedFlow<Map<Int, List<PersonMovieCast>>> = _movieActors.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _movieActors.resetReplayCache()
    }
}
