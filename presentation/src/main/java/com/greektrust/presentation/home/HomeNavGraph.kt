package com.greektrust.presentation.home

import com.greektrust.presentation.feed.NewsFeedNavGraph

sealed class HomeNavGraph(val route: String) {
    data object Home : HomeNavGraph("home")
}