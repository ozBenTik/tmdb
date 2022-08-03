package data.movies.datasource

import com.example.core.data.movies.CreditsStore
import com.example.core.data.movies.RecommendationsStore
import data.movies.MoviesStore
import di.*
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    @Popular val popularStore: MoviesStore,
    @TopRated val topRatedStore: MoviesStore,
    @Upcoming val upcomingStore: MoviesStore,
    @NowPlaying val nowPlayingStore: MoviesStore,
    @Discovery val discoveryStore: MoviesStore,
    val recommendationsStore: RecommendationsStore,
    val creditsStore: CreditsStore,
)