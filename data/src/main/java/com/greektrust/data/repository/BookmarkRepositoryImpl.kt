package com.greektrust.data.repository

import com.greektrust.data.datasource.local.BookmarkedArticleEntity
import com.greektrust.data.datasource.local.dao.BookmarkDao
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.mapper.toArticle
import com.greektrust.data.model.mapper.toBookmarkedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun getBookmarks(): Flow<List<Article>> {
        return bookmarkDao
            .getAllBookmarks()
            .map { entities ->
                entities.map { entity ->
                    entity.toArticle()
                }
            }
    }

    override fun getBookmarkByUrl(url: String): Flow<Article?> {
        return bookmarkDao
            .getBookmarkByUrl(url)
            .map { entity ->
                entity?.toArticle()
            }
    }

    override suspend fun insertBookmark(article: Article) {
        bookmarkDao.insert(article.toBookmarkedEntity())
    }

    override suspend fun deleteBookmark(url: String) {
        bookmarkDao.deleteByUrl(url)
    }

}
