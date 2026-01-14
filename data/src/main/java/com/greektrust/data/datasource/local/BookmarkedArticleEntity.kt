package com.greektrust.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bookmarked_articles")
data class BookmarkedArticleEntity(
    @PrimaryKey
    val url: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val sourceName: String,
    val publishedAt: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)