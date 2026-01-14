package com.greektrust.common.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDate(date: String): String {
    return try {
        val instant = Instant.parse(date)
        val formatter = DateTimeFormatter
            .ofPattern("dd MMM yyyy")
            .withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        ""
    }
}


val OpenInBrowserIcon: ImageVector
    get() {
        return ImageVector.Builder(
            name = "OpenInBrowser",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {

            // outer rectangle (browser window)
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3f, 4f)
                lineTo(21f, 4f)
                lineTo(21f, 20f)
                lineTo(3f, 20f)
                close()
            }

            // arrow
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(10f, 10f)
                lineTo(14f, 10f)
                lineTo(14f, 14f)
                lineTo(12f, 12f)
                lineTo(10f, 14f)
                close()
            }

        }.build()
    }