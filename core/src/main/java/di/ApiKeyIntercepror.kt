package di

import com.example.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().let { request ->
            val updatedUrl = request.url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val updatedRequest = request.newBuilder().url(updatedUrl).build()
            chain.proceed(updatedRequest)
        }
}