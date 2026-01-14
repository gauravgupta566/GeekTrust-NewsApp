package com.greektrust.data.repository

import com.greektrust.core.network.APIResult
import com.greektrust.data.datasource.remote.NewsRemoteDataSource
import com.greektrust.data.model.dto.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepository {


    override fun getsNewsFeed(): Flow<APIResult<List<Article>>> =
        flow {
            emit(newsRemoteDataSource.fetchNewsData())
        }

    override fun getsSearchNews(query: String): Flow<APIResult<List<Article>>> =
        flow {
            emit(newsRemoteDataSource.fetchSearchNews(query))
        }


}