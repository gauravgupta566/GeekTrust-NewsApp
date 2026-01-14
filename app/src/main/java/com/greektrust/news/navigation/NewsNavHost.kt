package com.greektrust.news.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greektrust.common.ui.utils.openCustomTabSafe
import com.greektrust.presentation.bookmark.BookMarkScreen
import com.greektrust.presentation.bookmark.BookmarkNavGraph
import com.greektrust.presentation.details.DetailsNavGraph
import com.greektrust.presentation.details.DetailsScreen
import com.greektrust.presentation.feed.NewsFeedNavGraph
import com.greektrust.presentation.feed.NewsFeedScreen
import com.greektrust.presentation.search.SearchNavGraph
import com.greektrust.presentation.search.SearchScreen

@Composable
fun NewsNavHost(modifier: Modifier, heading: (String) -> Unit) {

    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(navController, SearchNavGraph.Search.route, modifier = modifier) {

        composable(NewsFeedNavGraph.List.route) {
            NewsFeedScreen(heading, onArticleClick = { url ->
                navController.navigate(
                    DetailsNavGraph.Details.createRoute(url)
                )
            })
        }

        composable(
            DetailsNavGraph.Details.route, arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            )) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url") ?: return@composable
            val url = Uri.decode(encodedUrl)
            DetailsScreen(
                url = url,
                onBack = { navController.popBackStack() },
                onOpenInBrowser = {
                    openCustomTabSafe(context, url)
                },
                onShare = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, url)
                    }
                    context.startActivity(
                        Intent.createChooser(shareIntent, "Share article")
                    )
                },
                onBookmark = {})
        }


        composable(BookmarkNavGraph.Bookmark.route) {
            BookMarkScreen(heading, onArticleClick = { url ->
                navController.navigate(
                    DetailsNavGraph.Details.createRoute(url)
                )
            })
        }

        composable(SearchNavGraph.Search.route) {
            SearchScreen(heading)
        }
    }

}