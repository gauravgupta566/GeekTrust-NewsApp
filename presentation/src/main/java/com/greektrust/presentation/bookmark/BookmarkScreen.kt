package com.greektrust.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.greektrust.common.ui.extension.showToast
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.common.ui.utils.AppError
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.feed.NewsFeedContent

@Composable
fun BookMarkScreen(
    onArticleClick: (Article) -> Unit,
    bookmarkViewModel: BookMarkViewModel
) {
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        bookmarkViewModel.bookmarkEvent.collect { event ->
            when (event) {
                BookmarkEvent.Added ->
                    context.showToast("Article bookmarked")

                BookmarkEvent.Removed ->
                    context.showToast("Bookmark removed")
            }
        }
    }


    if (bookmarks.isEmpty()) {
        AppError("No bookmarks added yet")
    } else {
        NewsFeedContent(
            articles = bookmarks,
            onArticleClick = onArticleClick,
            onLoadMore = { }
        )
    }
}