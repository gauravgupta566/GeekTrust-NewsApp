package com.greektrust.common.ui.utils

sealed class AppBarState {

    data object Hidden : AppBarState()

    data class NewsFeed(val title: String, val onSearchClick :(String)-> Unit) : AppBarState()

    data class Bookmark(
        val title: String
    ) : AppBarState()

    data class Search(
        val title: String
    ) : AppBarState()

    data class Details(
        val title: String,
        val onBack: () -> Unit,
        val onShare: () -> Unit,
        val onBookmark: () -> Unit,
        val onOpenInBrowser: () -> Unit
    ) : AppBarState()
}
