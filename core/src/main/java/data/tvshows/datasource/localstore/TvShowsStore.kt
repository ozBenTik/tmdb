package com.example.core.data.tvshows.datasource.localstore

import com.example.model.movie.Movie
import com.example.model.tvshow.TvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.Cache.Companion.key
import javax.inject.Inject

class TvShowsStore @Inject constructor() {
    private val _tvShows  = MutableSharedFlow<Map<Int, List<TvShow>>>(replay = 1)

    fun observeEntries(): SharedFlow<Map<Int, List<TvShow>>> = _tvShows.asSharedFlow()

    fun insert(page: Int, tvShows: List<TvShow>) {
        if (page == 1) {
            deleteAll()
            _tvShows.tryEmit(mapOf(page to tvShows))
        } else {
            updatePage(page, tvShows)
        }
    }

    private fun updatePage(page: Int, tvShows: List<TvShow>) {
        val map = _tvShows.replayCache.first().toMutableMap()
        map[page] = tvShows
        _tvShows.tryEmit(map)
    }

    fun deletePage(page: Int) {
        val map = _tvShows.replayCache.first().toMutableMap()
        map.remove(page)
        _tvShows.tryEmit(map)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _tvShows.resetReplayCache()
    }

    fun getLastPage(): Int {
        return _tvShows.replayCache.firstOrNull()?.let { map ->
            map.maxOf { it.key }
        } ?: 0
    }

    fun getTvShowsForPage(page:Int) = _tvShows.replayCache.firstOrNull()?.let { it[page] }
}