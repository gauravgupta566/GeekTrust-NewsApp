package com.greektrust.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.greektrust.data.datasource.local.BookmarkedArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarked_articles ORDER BY bookmarkedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkedArticleEntity>>

    @Query("SELECT * FROM bookmarked_articles WHERE url = :url LIMIT 1")
    fun getBookmarkByUrl(url: String): Flow<BookmarkedArticleEntity?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: BookmarkedArticleEntity) :Long

    @Delete
    suspend fun delete(article: BookmarkedArticleEntity): Int

    @Query("DELETE FROM bookmarked_articles WHERE url = :url")
    suspend fun deleteByUrl(url: String) :Int
}