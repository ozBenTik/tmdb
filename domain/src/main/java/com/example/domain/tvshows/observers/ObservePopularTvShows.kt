package com.example.domain.tvshows.observers

import com.example.core.data.tvshows.TvShowsRepository
import com.example.domain.FlowInteractor
import com.example.domain.SubjectInteractor
import com.example.domain.movies.observers.ObserveTopRatedMovies
import com.example.model.movie.Movie
import com.example.model.tvshow.TvShow
import com.google.android.gms.common.config.GservicesValue.value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObservePopularTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
): SubjectInteractor<ObservePopularTvShows.Params, List<TvShow>>() {

    override fun createObservable(params: Params): Flow<List<TvShow>> {
        return tvShowsRepository.observePopular()
            .map { list -> list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}