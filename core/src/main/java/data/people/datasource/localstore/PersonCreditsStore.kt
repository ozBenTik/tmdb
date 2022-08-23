package com.example.core.data.people.datasource.localstore

import com.example.model.person.PersonCreditsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonCreditsStore @Inject constructor() {

    // Map<personId, List<PersonKnownFor>>
    private val _credits = MutableSharedFlow<Map<Int, PersonCreditsResponse>>(replay = 1)

    fun insert(personId: Int, credits: PersonCreditsResponse) {
        if (_credits.replayCache.isEmpty()) {
            _credits.tryEmit(mapOf(personId to credits))
        } else {
            val map = _credits.replayCache.first().toMutableMap()
            map[personId] = credits
            _credits.tryEmit(map)
        }
    }

    fun observeEntries(): SharedFlow<Map<Int, PersonCreditsResponse>> = _credits.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _credits.resetReplayCache()
    }

}