package com.greektrust.common.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator()
    }

}

@Composable
fun AppError(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Text(text = message)
    }

}


fun openCustomTabSafe(
    context: Context,
    url: String
) {
    val uri = Uri.parse(url)
    val customTabsIntent = CustomTabsIntent.Builder().build()

    try {
        customTabsIntent.launchUrl(context, uri)
    } catch (e: Exception) {
        // Fallback to default browser
        context.startActivity(
            Intent(Intent.ACTION_VIEW, uri)
        )
    }
}