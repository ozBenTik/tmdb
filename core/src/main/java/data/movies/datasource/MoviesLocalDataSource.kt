package data.movies.datasource

import di.*
import com.example.core.data.movies.datasource.localstore.CreditsStore
import com.example.core.data.movies.datasource.localstore.GenresStore
import com.example.core.data.movies.datasource.localstore.RecommendationsStore
import com.example.core.data.movies.datasource.localstore.MoviesStore
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
    @Discovery val discoveryStore: MoviesStore,
    val recommendationsStore: RecommendationsStore,
    val creditsStore: CreditsStore,
    val genresStore: GenresStore
)