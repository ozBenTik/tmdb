package di

import com.example.core.data.movies.datasource.localstore.CreditsStore
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.core.data.movies.datasource.localstore.RecommendationsStore
import com.example.core.data.people.datasource.localstore.PopularPeopleStore
import com.example.core.data.tvshows.datasource.localstore.TvShowsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StoreModule {

    @Singleton
    @Provides
    @PopularMovies
    fun providePopularMoviesStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @NowPlayingMovies
    fun provideNowPlayingMoviesStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @UpcomingMovies
    fun provideUpcomingMoviesStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @TopRatedMovies
    fun provideTopRatedMoviesStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @Discovery
    fun provideDiscoveryStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    fun provideRecommendationsStore(): RecommendationsStore = RecommendationsStore()

    @Singleton
    @Provides
    fun provideActorsStore(): CreditsStore = CreditsStore()

    @Singleton
    @Provides
    @PopularTvShows
    fun providePopularTvShowsStore() : TvShowsStore = TvShowsStore()

    @Singleton
    @Provides
    @TopRatedTvShows
    fun provideTopRatedTvShowsStore() : TvShowsStore = TvShowsStore()

    @Singleton
    @Provides
    @AiringTodayTvShows
    fun provideAiringTodayTvShowsStore() : TvShowsStore = TvShowsStore()

    @Singleton
    @Provides
    @OnAirTvShows
    fun provideOnAirTvShowsStore() : TvShowsStore = TvShowsStore()
}