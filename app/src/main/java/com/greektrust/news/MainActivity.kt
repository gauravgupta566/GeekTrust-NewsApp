package com.greektrust.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.greektrust.common.ui.TopBar
import com.greektrust.common.ui.theme.NewsAppGreekTrustTheme
import com.greektrust.common.ui.utils.AppBarState
import com.greektrust.common.ui.utils.OpenInBrowserIcon
import com.greektrust.news.navigation.NewsNavHost
import com.greektrust.presentation.details.DetailsNavGraph
import com.greektrust.presentation.details.DetailsScreen
import com.greektrust.presentation.feed.NewsFeedScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var appBarState by remember { mutableStateOf<AppBarState>(AppBarState.Hidden) }


            NewsAppGreekTrustTheme {
                Scaffold(
                    topBar = {
                        when (val state = appBarState) {
                            is AppBarState.Hidden -> {

                            }

                            is AppBarState.NewsFeed -> {
                                TopAppBar(
                                    title = { Text("News Feed") },
                                    actions = {
                                        IconButton(onClick = { state.onSearchClick }) {
                                            Icon(Icons.Default.Search, null)
                                        }
                                    })
                            }

                            is AppBarState.Search -> {
                                TopAppBar(title = { Text(state.title) })

                            }

                            is AppBarState.Bookmark -> {
                                TopAppBar(title = { Text(state.title) })
                            }

                            is AppBarState.Details -> {
                                TopAppBar(
                                    title = { Text(state.title) },
                                    navigationIcon = {
                                        IconButton(onClick = state.onBack) {
                                            Icon(Icons.Default.ArrowBack, null)
                                        }
                                    },
                                    actions = {
                                        IconButton(onClick = state.onShare) {
                                            Icon(Icons.Default.Share, null)
                                        }
                                        IconButton(onClick = state.onBookmark) {
                                            Icon(Icons.Default.Star, null)
                                        }
                                        IconButton(onClick = state.onOpenInBrowser) {
                                            Icon(OpenInBrowserIcon, null)
                                        }
                                    }
                                )
                            }

                        }
                    }

                ) { paddingValues ->
                    NewsNavHost(
                        Modifier
                            .padding(paddingValues)
                            .systemBarsPadding(),

                        appBarState = { newState ->
                            appBarState = newState
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppGreekTrustTheme {
        Greeting("Android")
    }
}