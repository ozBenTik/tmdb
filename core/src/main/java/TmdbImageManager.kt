package com.example.core

import com.example.core.network.ConfigurationService
import com.example.model.util.Configuration
import com.example.model.util.TmdbImageUrlProvider
import extensions.bodyOrThrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import util.AppCoroutineDispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbImageManager @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val configurationService: ConfigurationService
) {
    private val imageProvider = MutableStateFlow(TmdbImageUrlProvider())

    fun getLatestImageProvider() = imageProvider.value

    suspend fun refreshConfiguration() {
        try {
            val response = withContext(dispatchers.io) {
                configurationService.getConfiguration().awaitResponse()
            }
            onConfigurationLoaded(response.bodyOrThrow())
        } catch (t: Throwable) {
            // TODO
        }
    }

    private fun onConfigurationLoaded(configuration: Configuration) {
        configuration.images?.also { images ->
            val newProvider = TmdbImageUrlProvider(
                images.secureBaseUrl!!,
                images.posterSizes ?: emptyList(),
                images.backdropSizes ?: emptyList(),
                images.logoSizes ?: emptyList()
            )
            imageProvider.value = newProvider
        }
    }
}