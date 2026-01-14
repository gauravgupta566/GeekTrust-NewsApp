package com.greektrust.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.greektrust.data.datasource.local.dao.BookmarkDao


@Database(
    entities = [BookmarkedArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}