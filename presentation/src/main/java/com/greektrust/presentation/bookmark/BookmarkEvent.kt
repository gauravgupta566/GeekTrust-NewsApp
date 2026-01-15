package com.greektrust.presentation.bookmark

sealed class BookmarkEvent {
    object Added : BookmarkEvent()
    object Removed : BookmarkEvent()
}
