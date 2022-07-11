package di

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().let { request ->
            val updatedUrl = request.url
                .newBuilder()
                .addQueryParameter("api_key", "b25ada0aaa9911135fa454b2ad8494be")
                .build()

            val updatedRequest = request.newBuilder().url(updatedUrl).build()
            chain.proceed(updatedRequest)
        }
}