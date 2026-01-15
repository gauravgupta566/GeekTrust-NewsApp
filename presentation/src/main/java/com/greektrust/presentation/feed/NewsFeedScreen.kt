package com.greektrust.presentation.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.greektrust.common.ui.utils.AppError
import com.greektrust.common.ui.utils.AppLoader
import com.greektrust.common.ui.utils.formatDate
import com.greektrust.core.network.APIError
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article

@Composable
fun NewsFeedScreen(
    onArticleClick: (Article) -> Unit,
    newsFeedViewModel: NewsFeedViewModel = hiltViewModel()
) {

    val result = newsFeedViewModel.newFeed.collectAsState()

    LaunchedEffect(Unit) {
        newsFeedViewModel.loadFirstPage()
    }

    when (result.value) {
        APIResult.Loading -> {
            if (newsFeedViewModel.getCurrentPageNo() <= 1) {
                AppLoader()
            }

        }

        is APIResult.Success -> {
            val articles = (result.value as APIResult.Success).data
            if (articles.isEmpty()) {
                AppError("No news available")
            } else {
                NewsFeedContent(articles = articles, onArticleClick = onArticleClick, onLoadMore = {
                    newsFeedViewModel.loadNextPage()
                })
            }
        }

        else -> {
            val apiError: APIError = (result.value as APIResult.Error<*>).error as APIError
            AppError(apiError.error)
        }
    }

}


@Composable
fun NewsList(
    articles: List<Article>,
    onLoadMore: () -> Unit,
    onArticleClick: (Article) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(items = articles, key = { _, articles -> articles.url }) { index, article ->
            if (index >= articles.size - 3) {
                onLoadMore()
            }
            NewsItem(article = article, onClick = { onArticleClick(article) })
        }
    }
}

@Composable
fun NewsFeedContent(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onLoadMore: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(items = articles, key = { _, articles -> articles.url }) { index, feed ->
            if (index >= articles.size - 3) {
                onLoadMore()
            }
            NewsItem(feed, onClick = { onArticleClick(feed) })
        }
    }
}

@Composable
fun NewsItem(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column {
            // Image
            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )


            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                // Title
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Description (optional)
                article.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Source + Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.source.name,
                        style = MaterialTheme.typography.labelSmall
                    )

                    Text(
                        text = formatDate(article.publishedAt),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

