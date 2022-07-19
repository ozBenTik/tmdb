package data.movies.datasource

import com.example.core.data.movies.CreditsStore
import data.movies.MoviesStore
import di.*
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    @Popular val popularStore: MoviesStore,
    @TopRated val topRatedStore: MoviesStore,
    @Upcoming val upcomingStore: MoviesStore,
    @NowPlaying val nowPlayingStore: MoviesStore,
    @Recommendations val recommendationsStore: MoviesStore,
    val creditsStore: CreditsStore,
)