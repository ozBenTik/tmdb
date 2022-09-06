package com.example.domain.tvshows.observers

import com.example.core.data.tvshows.TvShowsRepository
import com.example.domain.SubjectInteractor
import com.example.model.tvshow.TvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveAiringTodayShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
): SubjectInteractor<ObserveAiringTodayShows.Params, List<TvShow>>() {

    override fun createObservable(params: Params): Flow<List<TvShow>> {
        return tvShowsRepository.observeAiringToday()
            .map { list -> list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}