package data.movies.datasource

import com.example.core.data.movies.CreditsStore
import com.example.core.data.movies.RecommendationsStore
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
    val recommendationsStore: RecommendationsStore,
    val creditsStore: CreditsStore,
)