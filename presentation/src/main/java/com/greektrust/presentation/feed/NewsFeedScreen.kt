package com.greektrust.presentation.feed

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsFeedScreen(heading:(String)->Unit, newsFeedViewModel: NewsFeedViewModel = hiltViewModel()) {

    Text(text = "News feed Screen")

    LaunchedEffect(Unit) {
        heading("News feed")
    }

}