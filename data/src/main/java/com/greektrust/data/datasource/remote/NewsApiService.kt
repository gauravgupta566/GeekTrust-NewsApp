package com.greektrust.data.datasource.remote

import com.greektrust.data.model.dto.NewsFeedDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getNewsFeed(
        @Query("country") country : String = "us",
        @Query("apiKey") apiKey : String ="e6dc23dbf38a46e289f5c52542ad9dac",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsFeedDTO>


    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q") q : String ,
        @Query("apiKey") apiKey : String ="e6dc23dbf38a46e289f5c52542ad9dac"
    ): Response<NewsFeedDTO>




}