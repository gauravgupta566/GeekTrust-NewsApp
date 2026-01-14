package com.greektrust.data.model.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsFeedDTO(
    @Json(name = "status")
    val status: String,

    @Json(name = "totalResults")
    val totalResults: Int,

    @Json(name = "articles")
    val articles: List<Article> = emptyList()
)
@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "source")
    val source: Source,

    @Json(name = "author")
    val author: String? = null,

    @Json(name = "title")
    val title: String,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "url")
    val url: String,

    @Json(name = "urlToImage")
    val urlToImage: String? = null,

    @Json(name = "publishedAt")
    val publishedAt: String,

    @Json(name = "content")
    val content: String? = null
)



@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "id")
    val id: String? = null,

    @Json(name = "name")
    val name: String
)