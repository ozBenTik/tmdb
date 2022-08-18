package com.example.core.data.people.datasource.localstore

import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.model.Movie
import com.example.model.PersonDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonDetailsStore @Inject constructor() {

    // Map<personId, PersonResponse>
    private val _person = MutableSharedFlow<Map<Int, PersonDetails>>(replay = 1)

    fun insert(personId: Int, person: PersonDetails) {
        if (_person.replayCache.isEmpty()) {
            _person.tryEmit(mapOf(personId to person))
        } else {
            val map = _person.replayCache.first().toMutableMap()
            map[personId] = person
            _person.tryEmit(map)
        }
    }

    fun observeEntries(): SharedFlow<Map<Int, PersonDetails>> = _person.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _person.resetReplayCache()
    }


}