package com.greektrust.data.datasource.remote

import com.greektrust.core.network.APIError
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.mapper.toDomain
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsApiService: NewsApiService) {

    suspend fun fetchNewsData(): APIResult<List<Article>> {
        return try {
            val response = newsApiService.getNewsFeed("US")
            APIResult.Success(response.articles.map { it.toDomain() })

        } catch (e: HttpException) {
            APIResult.Error(APIError(error = e.message ?: "", code = e.code()))

        } catch (e: IOException) {

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