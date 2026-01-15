package com.greektrust.presentation.bookmark

import com.greektrust.data.model.dto.Article
import com.greektrust.data.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeBookmarkRepository : BookmarkRepository {

    private val bookmarksFlow = MutableStateFlow<List<Article>>(emptyList())

    override fun getBookmarks(): Flow<List<Article>> = bookmarksFlow

    override fun getBookmarkByUrl(url: String): Flow<Article?> =
        bookmarksFlow.map { list ->
            list.firstOrNull { it.url == url }
        }

    override suspend fun insertBookmark(article: Article):Long {
        return -1
    }

    override suspend fun deleteBookmark(url: String):Int {
        return 0
    }

}