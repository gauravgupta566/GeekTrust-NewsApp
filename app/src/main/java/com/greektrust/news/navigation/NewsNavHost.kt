package com.greektrust.news.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greektrust.common.ui.utils.openCustomTabSafe
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.bookmark.BookMarkViewModel
import com.greektrust.presentation.details.DetailsNavGraph
import com.greektrust.presentation.details.DetailsScreen
import com.greektrust.presentation.home.HomeNavGraph
import com.greektrust.presentation.home.HomeScreen
import com.greektrust.presentation.search.SearchNavGraph
import com.greektrust.presentation.search.SearchScreen

@Composable
fun NewsNavHost() {

    val context = LocalContext.current
    val navController = rememberNavController()



    val bookMarkViewModel: BookMarkViewModel = hiltViewModel()

    NavHost(navController, HomeNavGraph.Home.route) {


        composable(HomeNavGraph.Home.route) {
            HomeScreen(
                onArticleClick = { article ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("article", article)

                    navController.navigate(
                        DetailsNavGraph.Details.route
                    )
                },
                onSearchClick = {
                    navController.navigate(SearchNavGraph.Search.route)
                },
                bookMarkViewModel = bookMarkViewModel

            )
        }



        composable(
            DetailsNavGraph.Details.route
        ) { backStackEntry ->
            val article =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Article>("article")
                    ?: return@composable

            DetailsScreen(
                article = article,
                onBack = { navController.popBackStack() },
                onOpenInBrowser = {
                    openCustomTabSafe(context, article.url)
                },
                onShare = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, article.url)
                    }
                    context.startActivity(
                        Intent.createChooser(shareIntent, "Share article")
                    )
                },
                onBookmark = {},
                bookMarkViewModel = bookMarkViewModel

                )


        }



        composable(SearchNavGraph.Search.route) {
            SearchScreen()
        }
    }

}