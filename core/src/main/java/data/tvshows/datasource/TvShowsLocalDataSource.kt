package com.example.core.data.tvshows.datasource

import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import di.AiringToday
import di.OnAir
import di.Popular
import di.TopRated
import javax.inject.Inject

class TvShowsLocalDataSource @Inject constructor(
    @Popular val popularTvShows: TvShowsStore,
    @TopRated val topRatedTvShows: TvShowsStore,
    @OnAir val onTheAirTvShows: TvShowsStore,
    @AiringToday val airingTodayTvShows: TvShowsStore
)