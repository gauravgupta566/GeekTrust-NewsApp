package com.greektrust.data.model.mapper

import com.greektrust.data.datasource.local.BookmarkedArticleEntity
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.dto.Source


fun Article.toDomain(): Article{
    return Article(
        author = author.orEmpty(),
        content = content.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        source = source,
        title = title.orEmpty(),
        url = url,
        urlToImage = urlToImage
    )
}


fun Article.toBookmarkedEntity(): BookmarkedArticleEntity {
    return BookmarkedArticleEntity(
        url = url,              // PrimaryKey
        title = title,
        description = description ?: "",
        imageUrl = urlToImage ?: "",
        sourceName = source.name,
        publishedAt = publishedAt,
        bookmarkedAt = System.currentTimeMillis()
    )
}

fun BookmarkedArticleEntity.toArticle(): Article {
    return Article(
        source = Source(
            id = null,
            name = sourceName
        ),
        author = "",
        title = title,
        description = description,
        url = url,
        urlToImage = imageUrl,
        publishedAt = publishedAt,
        content = ""
    )
}
