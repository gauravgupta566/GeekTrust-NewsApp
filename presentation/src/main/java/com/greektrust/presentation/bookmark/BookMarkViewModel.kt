package com.greektrust.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greektrust.data.model.dto.Article
import com.greektrust.data.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository) :
    ViewModel() {


    private val _bookmarkEvent = MutableSharedFlow<BookmarkEvent>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val bookmarkEvent: SharedFlow<BookmarkEvent> = _bookmarkEvent


    val bookmarks: StateFlow<List<Article>> =
        bookmarkRepository
            .getBookmarks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun isBookmarked(url: String): StateFlow<Boolean> =
        bookmarkRepository
            .getBookmarkByUrl(url)
            .map { it != null }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )


    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val existing =
                bookmarkRepository
                    .getBookmarkByUrl(article.url)
                    .first()

            if (existing == null) {
                bookmarkRepository.insertBookmark(article)
                _bookmarkEvent.emit(BookmarkEvent.Added)

            } else {
                bookmarkRepository.deleteBookmark(article.url)
                _bookmarkEvent.emit(BookmarkEvent.Removed)
            }

        }
    }

}