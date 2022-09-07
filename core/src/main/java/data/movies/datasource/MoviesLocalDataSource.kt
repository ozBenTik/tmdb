package data.movies.datasource

import com.example.core.data.movies.datasource.localstore.CreditsStore
import com.example.core.data.movies.datasource.localstore.GenresStore
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.core.data.movies.datasource.localstore.RecommendationsStore
import di.*
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    @PopularMovies val popularStore: MoviesStore,
    @TopRatedMovies val topRatedStore: MoviesStore,
    @UpcomingMovies val upcomingStore: MoviesStore,
    @NowPlayingMovies val nowPlayingStore: MoviesStore,
    @Discovery val discoveryStore: MoviesStore,
    val recommendationsStore: RecommendationsStore,
    val creditsStore: CreditsStore,
    val genresStore: GenresStore
)