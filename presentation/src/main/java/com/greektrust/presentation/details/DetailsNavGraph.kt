package com.greektrust.presentation.details

import com.greektrust.presentation.feed.NewsFeedNavGraph

sealed class DetailsNavGraph(val route:String) {

    data object Details : DetailsNavGraph("newsDetails.{id}") {
        fun createRoute(id: String) = "newsDetails/id"
    }

}