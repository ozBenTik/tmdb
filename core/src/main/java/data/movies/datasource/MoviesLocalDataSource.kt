package data.movies.datasource

import data.movies.MoviesStore
import di.NowPlaying
import di.Popular
import di.TopRated
import di.Upcoming
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    @Popular val popularStore: MoviesStore,
    @TopRated val topRatedStore: MoviesStore,
    @Upcoming val upcomingStore: MoviesStore,
    @NowPlaying val nowPlayingStore: MoviesStore,
) {



}