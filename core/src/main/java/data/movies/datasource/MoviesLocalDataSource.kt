package data.movies.datasource

import com.example.core.data.movies.datasource.localstore.CreditsStore
import com.example.core.data.movies.datasource.localstore.GenresStore
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.core.data.movies.datasource.localstore.RecommendationsStore
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
    val genresStore: GenresStore
)