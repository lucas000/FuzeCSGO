package com.fuze.csgo.api

import com.fuze.csgo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header("No-Authentication") == null) {
            val apiKey = BuildConfig.API_KEY

            if (apiKey.isNotEmpty()) {
                val finalToken = "Bearer $apiKey"
                request = request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()
            }
        }
        return chain.proceed(request)
    }
}