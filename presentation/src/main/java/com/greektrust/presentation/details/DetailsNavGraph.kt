package com.greektrust.presentation.details

import android.net.Uri
import com.greektrust.presentation.feed.NewsFeedNavGraph

sealed class DetailsNavGraph(val route:String) {

    data object Details : DetailsNavGraph("newsDetails/{url}") {

        fun createRoute(url: String) = "newsDetails/${Uri.encode(url)}"
    }

}