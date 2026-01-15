package com.greektrust.news.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.common.ui.utils.openCustomTabSafe
import com.greektrust.presentation.bookmark.BookMarkScreen
import com.greektrust.presentation.bookmark.BookMarkViewModel
import com.greektrust.presentation.bookmark.BookmarkNavGraph
import com.greektrust.presentation.details.DetailsNavGraph
import com.greektrust.presentation.details.DetailsScreen
import com.greektrust.presentation.feed.NewsFeedNavGraph
import com.greektrust.presentation.feed.NewsFeedScreen
import com.greektrust.presentation.home.HomeNavGraph
import com.greektrust.presentation.home.HomeScreen
import com.greektrust.presentation.search.SearchNavGraph
import com.greektrust.presentation.search.SearchScreen

@Composable
fun NewsNavHost(modifier: Modifier, appBarState: (AppBarState) -> Unit) {

    val context = LocalContext.current
    val navController = rememberNavController()



    NavHost(navController, HomeNavGraph.Home.route, modifier = modifier) {

        composable(HomeNavGraph.Home.route) {
            HomeScreen(
                appBarState, onArticleClick = { url ->
                    navController.navigate(
                        DetailsNavGraph.Details.createRoute(url)
                    )
                },
                onSearchClick = { route->
                    navController.navigate(route) }


            )
        }

        composable(NewsFeedNavGraph.List.route) {
            NewsFeedScreen(
                appBarState, onArticleClick = { url ->
                    navController.navigate(
                        DetailsNavGraph.Details.createRoute(url)
                    )
                },
                onSearchClick = { route->
                    navController.navigate(route) }
            )
        }

        composable(
            DetailsNavGraph.Details.route, arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            )) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url") ?: return@composable
            val url = Uri.decode(encodedUrl)
            DetailsScreen(
                appBarState,
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
                onBookmark = {},

                )

        }


        composable(BookmarkNavGraph.Bookmark.route) {
            BookMarkScreen(appBarState, onArticleClick = { url ->
                navController.navigate(
                    DetailsNavGraph.Details.createRoute(url)
                )
            }
            )
        }

        composable(SearchNavGraph.Search.route) {
            SearchScreen(appBarState)
        }
    }

}