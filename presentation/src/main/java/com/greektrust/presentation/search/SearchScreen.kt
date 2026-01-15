package com.greektrust.presentation.search

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.common.ui.utils.AppError
import com.greektrust.common.ui.utils.AppLoader
import com.greektrust.common.ui.utils.formatDate
import com.greektrust.core.network.APIError
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }

    val result by viewModel.data.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.search_news_feed)) },
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 🔍 Search Input
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.onQueryChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.search_articles)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 📊 Result State
            when (result) {
                APIResult.Loading -> {
                    if (query.isNotEmpty() && query.length > 2)
                        AppLoader()
                }

                is APIResult.Success -> {

                    val articles = (result as APIResult.Success).data
                    if (articles.isEmpty()) {
                        AppError(stringResource(R.string.no_news_available))
                    } else {
                        SearchItemList(articles)
                    }
                }

                else -> {
                    val apiError: APIError = (result as APIResult.Error<*>).error as APIError
                    AppError(apiError.error)
                }


            }
        }
    }
}


@Composable
fun SearchItemList(articles: List<Article>) {
    LazyColumn {
        items(
            items = articles,
            key = { it -> it.url }
        ) { article ->
            SearchItem(
                article = article,
            )
        }
    }
}

@Composable
fun SearchItem(article: Article) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
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