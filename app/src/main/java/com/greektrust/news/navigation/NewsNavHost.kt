package com.greektrust.news.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.greektrust.presentation.bookmark.BookMarkScreen
import com.greektrust.presentation.bookmark.BookmarkNavGraph
import com.greektrust.presentation.details.DetailsNavGraph
import com.greektrust.presentation.details.DetailsScreen
import com.greektrust.presentation.feed.NewsFeedNavGraph
import com.greektrust.presentation.feed.NewsFeedScreen
import com.greektrust.presentation.search.SearchNavGraph
import com.greektrust.presentation.search.SearchScreen

@Composable
fun NewsNavHost() {

    val navController = rememberNavController()

    NavHost(navController, NewsFeedNavGraph.List.route) {

        println("hello ${NewsFeedNavGraph.List.route}")

        composable(NewsFeedNavGraph.List.route){
           NewsFeedScreen()
        }

        composable(DetailsNavGraph.Details.route){
            DetailsScreen()
        }

        composable(BookmarkNavGraph.Bookmark.route){
            BookMarkScreen()
        }

        composable(SearchNavGraph.Search.route){
            SearchScreen()
        }

    }

}