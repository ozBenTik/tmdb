package com.example.domain.tvshows.observers

import com.example.core.data.tvshows.TvShowsRepository
import com.example.domain.SubjectInteractor
import com.example.model.tvshow.TvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveOnAirTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
): SubjectInteractor<ObserveOnAirTvShows.Params, List<TvShow>>() {

    override fun createObservable(params: Params): Flow<List<TvShow>> {
        return tvShowsRepository.observeOnAir()
            .map { list -> list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}