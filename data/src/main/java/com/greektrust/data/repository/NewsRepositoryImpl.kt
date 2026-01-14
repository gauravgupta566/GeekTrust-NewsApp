package com.greektrust.data.repository

import com.greektrust.core.network.APIResult
import com.greektrust.data.datasource.remote.NewsRemoteDataSource
import com.greektrust.data.model.dto.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepository {


    override suspend fun getsNewsFeed(): Flow<APIResult<List<Article>>> {
        return flow {
            emit(newsRemoteDataSource.fetchNewsData())
        }
    }

}