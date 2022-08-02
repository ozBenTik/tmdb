package com.example.core.data.movies.datasource.localstore

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class DataByIdStore<T> {

    // Map<id, T>
    private val _data = MutableSharedFlow<Map<Int, T>>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun insert(movieId: Int, data: T) {
        _data.resetReplayCache()
        _data.tryEmit(mapOf(movieId to data))
    }

    fun observeEntries(): SharedFlow<Map<Int, T>> = _data.asSharedFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _data.resetReplayCache()
    }
}