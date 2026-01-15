package com.greektrust.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(
    private val maxRetries: Int = 3,
    private val initialDelayMs: Long = 500
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var attempt = 0
        var lastException: IOException? = null

        while (attempt < maxRetries) {
            try {
                val response = chain.proceed(chain.request())

                // Retry only on server errors
                if (response.isSuccessful || response.code !in 500..599) {
                    return response
                }
            } catch (e: IOException) {
                lastException = e
            }

            attempt++
            Thread.sleep(initialDelayMs * attempt) // exponential-ish backoff
        }

        throw lastException ?: IOException("Network retry failed")
    }
}
