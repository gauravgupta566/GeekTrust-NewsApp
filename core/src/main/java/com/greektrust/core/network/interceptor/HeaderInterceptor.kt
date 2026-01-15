package com.greektrust.core.network.interceptor

import com.greektrust.core.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request()
                .newBuilder()
                .addHeader(
                    "User-Agent",
                    "Android"
                ).build()


        return chain.proceed(newRequest)
    }
}