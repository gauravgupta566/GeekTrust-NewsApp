package com.greektrust.presentation.feed

sealed class NewsFeedNavGraph(val route: String) {

    data object List : NewsFeedNavGraph("newsFeed")



}