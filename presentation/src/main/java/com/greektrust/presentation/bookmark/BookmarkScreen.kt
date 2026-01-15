package com.greektrust.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.greektrust.common.ui.extension.showToast
import com.greektrust.common.ui.utils.AppError
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.R
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
                    context.showToast(context.getString(R.string.article_bookmarked))

                BookmarkEvent.Removed ->
                    context.showToast(context.getString(R.string.bookmark_removed))
            }
        }
    }


    if (bookmarks.isEmpty()) {
        AppError(stringResource(R.string.no_bookmarks_added_yet))
    } else {
        NewsFeedContent(
            articles = bookmarks,
            onArticleClick = onArticleClick,
            onLoadMore = { }
        )
    }
}