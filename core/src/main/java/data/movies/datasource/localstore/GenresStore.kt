package com.example.core.data.movies.datasource.localstore

import com.example.model.Genre
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresStore @Inject constructor(){

    private val _genres = MutableSharedFlow<List<Genre>>(replay = 1).apply {
        tryEmit(emptyList())
    }

    fun insert(genres: List<Genre>) {
        _genres.tryEmit(genres)
    }

    fun observeEntries(): SharedFlow<List<Genre>> = _genres.asSharedFlow()
}