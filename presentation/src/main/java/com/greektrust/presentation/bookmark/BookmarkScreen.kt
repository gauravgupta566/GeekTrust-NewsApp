package com.greektrust.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.greektrust.common.ui.utils.AppError
import com.greektrust.presentation.feed.NewsFeedContent

@Composable
fun BookMarkScreen(
    heading: (String) -> Unit,
    onArticleClick: (String) -> Unit,
    bookmarkViewModel: BookMarkViewModel = hiltViewModel()
) {
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState()

    LaunchedEffect(Unit) {
        heading("Bookmarks")
    }

    if (bookmarks.isEmpty()) {
        AppError("No bookmarks added yet")
    } else {
        // 🔁 Reuse SAME UI as News Feed
        NewsFeedContent(
            articles = bookmarks,
            onArticleClick = onArticleClick
        )
    }
}