package com.greektrust.presentation.search

import com.greektrust.presentation.feed.NewsFeedNavGraph

sealed class SearchNavGraph(val route:String) {
    data object Search : SearchNavGraph("search")

}