package com.example.core.data.people.datasource.localstore
import com.example.model.PopularPerson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularPeopleStore @Inject constructor() {

    // Map<Page, people>
    private val _people = MutableSharedFlow<Map<Int, List<PopularPerson>>>(replay = 1)

    fun insert(page: Int, people: List<PopularPerson>) {
        if (page == 1) {
            deleteAll()
            _people.tryEmit(mapOf(page to people))
        } else {
            updatePage(page, people)
        }
    }
    fun observeEntries(): SharedFlow<Map<Int, List<PopularPerson>>> = _people.asSharedFlow()

    private fun updatePage(page: Int, people: List<PopularPerson>) {
        val map = _people.replayCache.first().toMutableMap()
        map[page] = people
        _people.tryEmit(map)
    }

    fun deletePage(page: Int) {
        val map = _people.replayCache.first().toMutableMap()
        map.remove(page)
        _people.tryEmit(map)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun deleteAll() {
        _people.resetReplayCache()
    }

    fun getLastPage(): Int {
        return _people.replayCache.firstOrNull()?.let { map ->
            map.maxOf { it.key }
        } ?: 0
    }

    fun getPeopleForPage(page:Int) = _people.replayCache.firstOrNull()?.let { it[page] }
}