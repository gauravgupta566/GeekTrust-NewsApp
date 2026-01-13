package com.greektrust.presentation.bookmark

import com.greektrust.presentation.feed.NewsFeedNavGraph

sealed class BookmarkNavGraph(val route: String) {

    data object Bookmark : BookmarkNavGraph("bookmark")

}