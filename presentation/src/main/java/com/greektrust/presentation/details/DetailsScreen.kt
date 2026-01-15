package com.greektrust.presentation.details

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.presentation.bookmark.BookMarkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    appBarState: (AppBarState) -> Unit,
    url: String,
    onBack: () -> Unit,
    onOpenInBrowser: () -> Unit,
    onShare: () -> Unit,
    onBookmark: () -> Unit,
    bookMarkViewModel: BookMarkViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        appBarState(
            AppBarState.Details(
                title = "Details",
                onBack = onBack,
                onShare = onShare,
                onBookmark = onBookmark,
                onOpenInBrowser = onOpenInBrowser
            )
        )
    }

    ArticleWebView(
        url = url,
    )

}

@Composable
fun ArticleWebView(
    url: String,
) {
    AndroidView(
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