package com.greektrust.presentation.details

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.greektrust.common.ui.extension.showToast
import com.greektrust.common.ui.utils.BookmarkedIcon
import com.greektrust.common.ui.utils.NotBookmarkedIcon
import com.greektrust.common.ui.utils.OpenInBrowserIcon
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.bookmark.BookMarkViewModel
import com.greektrust.presentation.bookmark.BookmarkEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    article: Article,
    onBack: () -> Unit,
    onOpenInBrowser: () -> Unit,
    onShare: () -> Unit,
    onBookmark: () -> Unit,
    bookMarkViewModel: BookMarkViewModel
) {

    val isBookMark by bookMarkViewModel.isBookmarked(article.url).collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    LaunchedEffect(bookMarkViewModel) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            bookMarkViewModel.bookmarkEvent.collect { event ->
                when (event) {
                    BookmarkEvent.Added ->
                        context.showToast("Article bookmarked")

                    BookmarkEvent.Removed ->
                        context.showToast("Bookmark removed")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onShare) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { bookMarkViewModel.toggleBookmark(article) }) {
                        Icon(
                            imageVector = if (isBookMark) {
                                BookmarkedIcon
                            } else {
                                NotBookmarkedIcon
                            }, contentDescription = "Bookmark"
                        )
                    }
                    IconButton(onClick = onOpenInBrowser) {
                        Icon(
                            imageVector = OpenInBrowserIcon,
                            contentDescription = "Open in browser"
                        )
                    }
                }
            )
        }
    ) { padding ->
        ArticleWebView(
            url = article.url,
            modifier = Modifier.padding(padding)
        )
    }

}

@Composable
fun ArticleWebView(
    url: String,
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        }
    )
}