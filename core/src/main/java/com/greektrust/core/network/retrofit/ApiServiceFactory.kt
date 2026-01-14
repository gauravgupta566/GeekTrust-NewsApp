package com.greektrust.core.network.retrofit

import retrofit2.Retrofit

object ApiServiceFactory {

    inline fun <reified T> create(
        retrofit: Retrofit
    ): T = retrofit.create(T::class.java)
}