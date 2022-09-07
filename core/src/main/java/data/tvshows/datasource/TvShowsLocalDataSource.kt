package com.example.core.data.tvshows.datasource

import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import di.*
import javax.inject.Inject

class TvShowsLocalDataSource @Inject constructor(
    @PopularTvShows val popularTvShows: TvShowsStore,
    @TopRatedTvShows val topRatedTvShows: TvShowsStore,
    @OnAirTvShows val onTheAirTvShows: TvShowsStore,
    @AiringTodayTvShows val airingTodayTvShows: TvShowsStore
)