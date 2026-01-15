package com.greektrust.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.data.model.dto.Article
import com.greektrust.presentation.bookmark.BookMarkScreen
import com.greektrust.presentation.bookmark.BookMarkViewModel
import com.greektrust.presentation.feed.NewsFeedScreen

@Composable
fun HomeScreen(
    appBarState: (AppBarState) -> Unit,
    onArticleClick: (String) -> Unit,
    onSearchClick : (String) -> Unit
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Bookmark
    )

    Scaffold(
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
            appBarState = appBarState,
            onArticleClick = onArticleClick,
            onSearchClick = onSearchClick,
        )
    }
}


@Composable
fun BottomNavHost(
    navController: NavHostController,
    padding: PaddingValues,
    appBarState: (AppBarState) -> Unit,
    onArticleClick: (String) -> Unit,
    onSearchClick: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.News.route,
        modifier = Modifier.padding(padding)
    ) {

        composable(BottomNavItem.News.route) {
            NewsFeedScreen(
                appBarState = appBarState,
                onArticleClick = onArticleClick,
                onSearchClick = onSearchClick
            )
        }

        composable(BottomNavItem.Bookmark.route) {
            BookMarkScreen(
                appBarState = appBarState,
                onArticleClick = onArticleClick,
            )
        }
    }
}

