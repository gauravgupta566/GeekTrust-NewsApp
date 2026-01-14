package com.greektrust.data.model.mapper

import com.greektrust.data.model.dto.Article


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

//fun ArticleDto.toDomain(): Article {
//    return Article(
//        title = title.orEmpty(),
//        description = description.orEmpty(),
//        imageUrl = urlToImage.orEmpty(),
//        articleUrl = url.orEmpty()
//    )
//}
