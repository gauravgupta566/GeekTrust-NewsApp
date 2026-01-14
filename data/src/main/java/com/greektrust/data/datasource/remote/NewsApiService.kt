package com.greektrust.data.datasource.remote

import com.greektrust.data.model.dto.NewsFeedDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getNewsFeed(
        @Query("country") country : String = "in"
    ): NewsFeedDTO


}