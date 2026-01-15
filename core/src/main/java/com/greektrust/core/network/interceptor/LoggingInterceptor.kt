package com.greektrust.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val startTime = System.currentTimeMillis()

        println("➡️ REQUEST")
        println("${request.method} ${request.url}")
        println("Headers: ${request.headers}")

        val response = chain.proceed(request)

        val duration = System.currentTimeMillis() - startTime

        println("⬅️ RESPONSE")
        println("Code: ${response.code}")
        println("Message: ${response.message}")
        println("Time: ${duration}ms")
        println("URL: ${response.request.url}")

        return response

    }
}