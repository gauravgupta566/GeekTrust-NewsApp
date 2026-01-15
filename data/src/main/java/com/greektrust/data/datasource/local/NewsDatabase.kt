package com.greektrust.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.greektrust.data.datasource.local.dao.BookmarkDao


@Database(
    entities = [BookmarkedArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}




val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL(
//            """
//            ALTER TABLE bookmarked_articles
//            ADD COLUMN bookmarkedAt INTEGER NOT NULL DEFAULT 0
//            """.trimIndent()
//        )
    }
}
