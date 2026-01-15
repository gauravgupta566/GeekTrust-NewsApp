package com.greektrust.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.common.ui.utils.AppError
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.feed.NewsFeedContent

@Composable
fun BookMarkScreen(
    appBarState: (AppBarState) -> Unit,
    onArticleClick: (String) -> Unit,
    bookmarkViewModel: BookMarkViewModel = hiltViewModel()
) {
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState()

    LaunchedEffect(Unit) {
        appBarState(AppBarState.Bookmark(title = "BookMark"))
    }

    if (bookmarks.isEmpty()) {
        AppError("No bookmarks added yet")
    } else {
        NewsFeedContent(
            articles = bookmarks,
            onArticleClick = onArticleClick
        )
    }
}