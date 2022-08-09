package di

import com.example.core.network.ConfigurationService
import com.example.core.network.PeopleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import network.MoviesService
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

    @Singleton
    @Provides
    fun provideConfigurationService(retrofit: Retrofit): ConfigurationService =
        retrofit.create(ConfigurationService::class.java)

    @Singleton
    @Provides
    fun providePeopleServiceService(retrofit: Retrofit): PeopleService =
        retrofit.create(PeopleService::class.java)

}