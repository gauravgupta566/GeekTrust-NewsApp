package com.greektrust.data.datasource.remote

import com.greektrust.core.network.APIError
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.mapper.toDomain
import java.io.IOException
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsApiService: NewsApiService) {

    suspend fun fetchSearchNews(query:String): APIResult<List<Article>> {
        return try {
            val response = newsApiService.getSearchNews(query)
            if (response.isSuccessful) {
                val result = response.body()
                APIResult.Success(result!!.articles.map { it.toDomain() })
            } else {
                APIResult.Error(
                    APIError(
                        code = response.code(),
                        error = response.errorBody()?.string() ?: "Unknown error"
                    )
                )
            }

        }  catch (e: IOException) {
            APIResult.Error(
                APIError(
                    error = "Network error. Please check your connection."
                )
            )

        } catch (e: Exception) {
            APIResult.Error(APIError(error = e.message ?: ""))
        }

    }

    suspend fun fetchNewsData(): APIResult<List<Article>> {
        return try {
            val response = newsApiService.getNewsFeed("us")
            if (response.isSuccessful) {
                val result = response.body()
                APIResult.Success(result!!.articles.map { it.toDomain() })
            } else {
                APIResult.Error(
                    APIError(
                        code = response.code(),
                        error = response.errorBody()?.string() ?: "Unknown error"
                    )
                )
            }

        }  catch (e: IOException) {
            APIResult.Error(
                APIError(
                    error = "Network error. Please check your connection."
                )
            )

        } catch (e: Exception) {
            APIResult.Error(APIError(error = e.message ?: ""))
        }

    }
}