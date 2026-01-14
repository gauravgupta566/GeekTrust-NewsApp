package com.greektrust.data.repository

import com.greektrust.data.datasource.local.BookmarkedArticleEntity
import com.greektrust.data.model.dto.Article
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarks(): Flow<List<Article>>

    fun getBookmarkByUrl(url: String): Flow<Article?>


    suspend fun insertBookmark(article: Article)

    suspend fun deleteBookmark(url: String)

}
