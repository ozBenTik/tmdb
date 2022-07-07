package db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun providePopularDao(appDatabase: AppDatabase): PopularDao {
        return appDatabase.popularDao()
    }

    @Singleton
    @Provides
    fun provideUpcomingDao(appDatabase: AppDatabase): UpcomingDao {
        return appDatabase.upcomingDao()
    }

    @Singleton
    @Provides
    fun provideTopRatedDao(appDatabase: AppDatabase): TopRatedDao {
        return appDatabase.topRatedDao()
    }

    @Singleton
    @Provides
    fun providePlantDao(appDatabase: AppDatabase): NowPlayingDao {
        return appDatabase.nowPlayingDao()
    }

}