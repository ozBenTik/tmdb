package di

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()

        val updatedUrl = req
            .url.newBuilder()
            .addQueryParameter("api_key", "b25ada0aaa9911135fa454b2ad8494be")
            .build()

        val updatedRequest = req
            .newBuilder()
            .url(updatedUrl)
            .build()

        return chain.proceed(updatedRequest)
    }

}