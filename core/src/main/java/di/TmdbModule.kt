package com.example.core.di

import com.example.core.TmdbImageManager
import com.example.model.util.TmdbImageUrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object TmdbModule {

    @Provides
    fun provideTmdbImageUrlProvider(tmdbManager: TmdbImageManager): TmdbImageUrlProvider {
        return tmdbManager.getLatestImageProvider()
    }
}