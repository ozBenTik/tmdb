package com.example.core.data.people.datasource.localstore

import com.example.model.PersonCredits
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonCreditsStore @Inject constructor() {

    // Map<personId, List<PersonKnownFor>>
    private val _knownFor = MutableSharedFlow<Map<Int, List<PersonCredits>>>(replay = 1)

    fun insert(personId: Int, knownFor: List<PersonCredits>) {
        if (_knownFor.replayCache.isEmpty()) {
            _knownFor.tryEmit(mapOf(personId to knownFor))
        } else {
            val map = _knownFor.replayCache.first().toMutableMap()
            map[personId] = knownFor
            _knownFor.tryEmit(map)
        }
    }

    fun observeEntries(): SharedFlow<Map<Int, List<PersonCredits>>> = _knownFor.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _knownFor.resetReplayCache()
    }

}