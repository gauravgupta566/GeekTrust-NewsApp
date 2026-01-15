package com.greektrust.data.repository

import androidx.paging.PagingData
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.dto.NewsFeedDTO
import kotlinx.coroutines.flow.Flow



interface NewsRepository {

    fun getsNewsFeed(page:Int,pageSize: Int): Flow<APIResult<List<Article>>>


    fun getsSearchNews(query:String): Flow<APIResult<List<Article>>>


}