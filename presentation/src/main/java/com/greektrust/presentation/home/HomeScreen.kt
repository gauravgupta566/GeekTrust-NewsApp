package com.greektrust.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.R
import com.greektrust.presentation.bookmark.BookMarkScreen
import com.greektrust.presentation.bookmark.BookMarkViewModel
import com.greektrust.presentation.feed.NewsFeedScreen
import com.greektrust.presentation.search.SearchNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onArticleClick: (Article) -> Unit,
    onSearchClick: () -> Unit,
    bookMarkViewModel : BookMarkViewModel
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Bookmark
    )

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value?.destination?.route

    Scaffold(
        topBar = {
            when (currentRoute) {
                BottomNavItem.News.route -> {
                    TopAppBar(
                        title = { Text(stringResource(R.string.news_feed)) },
                        actions = {
                            IconButton(
                                onClick = {
                                    onSearchClick()
                                }
                            ) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        }
                    )
                }

                BottomNavItem.Bookmark.route -> {
                    TopAppBar(
                        title = { Text(stringResource(R.string.bookmarks)) }
                    )
                }


            }
        },

        bottomBar = {
            NavigationBar {
                val currentRoute = navController
                    .currentBackStackEntryAsState().value
                    ?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->
        BottomNavHost(
            navController = navController,
            padding = padding,
            onArticleClick = onArticleClick,
            bookMarkViewModel = bookMarkViewModel
        )
    }
}


@Composable
fun BottomNavHost(
    navController: NavHostController,
    padding: PaddingValues,
    onArticleClick: (Article) -> Unit,
    bookMarkViewModel: BookMarkViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.News.route,
        modifier = Modifier.padding(padding)
    ) {

        composable(BottomNavItem.News.route) {
            NewsFeedScreen(
                onArticleClick = onArticleClick

            )
        }

        composable(BottomNavItem.Bookmark.route) {
            BookMarkScreen(
                onArticleClick = onArticleClick,
                bookmarkViewModel = bookMarkViewModel

            )
        }
    }
}

