package com.greektrust.data.di

import com.greektrust.data.datasource.remote.NewsApiService
import com.greektrust.data.datasource.remote.NewsRemoteDataSource
import com.greektrust.data.repository.NewsRepository
import com.greektrust.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideNewsApi(
        retrofit: Retrofit
    ): NewsApiService =
        retrofit.create(NewsApiService::class.java)

    @Provides
    fun provideRemoteDataSource(
        api: NewsApiService
    ): NewsRemoteDataSource =
        NewsRemoteDataSource(api)

    @Provides
    fun provideNewsRepository(
        remoteDataSource: NewsRemoteDataSource
    ): NewsRepository =
        NewsRepositoryImpl(remoteDataSource)



}