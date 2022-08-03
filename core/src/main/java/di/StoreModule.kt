package di

import com.example.core.data.movies.CreditsStore
import com.example.core.data.movies.RecommendationsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.movies.MoviesStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StoreModule {

    @Singleton
    @Provides
    @Popular
    fun providePopularStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @NowPlaying
    fun provideNowPlayingStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @Upcoming
    fun provideUpcomingStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @TopRated
    fun provideTopRatedStore(): MoviesStore = MoviesStore()

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
}