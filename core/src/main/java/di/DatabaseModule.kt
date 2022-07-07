package di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import db.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePopularDao(appDatabase: AppDatabase): PopularDao {
        return appDatabase.popularDao()
    }

    @Provides
    fun provideUpcomingDao(appDatabase: AppDatabase): UpcomingDao {
        return appDatabase.upcomingDao()
    }
    
    @Provides
    fun provideTopRatedDao(appDatabase: AppDatabase): TopRatedDao {
        return appDatabase.topRatedDao()
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): NowPlayingDao {
        return appDatabase.nowPlayingDao()
    }

}