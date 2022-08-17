package com.example.core.data.people.datasource.localstore

import com.example.model.Person
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonDetailsStore @Inject constructor() {

    // Map<Page, PersonResponse>
    private val _person = MutableSharedFlow<Map<Int, Person>>(replay = 1)

    fun insert(personId: Int, person: Person) {
        _person.tryEmit(mapOf(personId to person))
    }

    fun observeEntries(): SharedFlow<Map<Int, Person>> = _person.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _person.resetReplayCache()
    }
}