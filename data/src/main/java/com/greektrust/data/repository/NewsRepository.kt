package com.greektrust.data.repository

import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import kotlinx.coroutines.flow.Flow



interface NewsRepository {

    fun getsNewsFeed(): Flow<APIResult<List<Article>>>


    fun getsSearchNews(query:String): Flow<APIResult<List<Article>>>


}