package com.greektrust.core.network.di

import com.greektrust.core.network.NetworkConstants
import com.greektrust.core.network.interceptor.HeaderInterceptor
import com.greektrust.core.network.interceptor.LoggingInterceptor
import com.greektrust.core.network.interceptor.RetryInterceptor
import com.greektrust.core.network.retrofit.RetrofitProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okio.Timeout
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

   const val BASE_URL = "https://newsapi.org/"
   const val API_KEY = "e6dc23dbf38a46e289f5c52542ad9dac"

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient,moshi: Moshi): Retrofit {
            return RetrofitProvider.create(BASE_URL, okHttpClient,moshi)
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(NetworkConstants.TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(HeaderInterceptor(API_KEY))
            .addInterceptor(RetryInterceptor())
            .build()


    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

}