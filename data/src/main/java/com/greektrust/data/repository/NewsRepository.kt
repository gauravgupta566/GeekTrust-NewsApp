package com.greektrust.data.repository

import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import kotlinx.coroutines.flow.Flow



interface NewsRepository {

   suspend fun getsNewsFeed(): Flow<APIResult<List<Article>>>

}