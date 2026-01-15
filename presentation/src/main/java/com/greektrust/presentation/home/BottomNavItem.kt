package com.greektrust.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object News : BottomNavItem(
        route = "newsFeed",
        label = "News",
        icon = Icons.Filled.Home
    )

    data object Bookmark : BottomNavItem(
        route = "bookmark",
        label = "Bookmarks",
        icon = Icons.Filled.Star
    )

}