package com.greektrust.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greektrust.data.model.dto.Article
import com.greektrust.data.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor (private val bookmarkRepository: BookmarkRepository) : ViewModel() {

    private val _bookmarks = MutableStateFlow<List<Article>>(emptyList())
    val bookmarks: StateFlow<List<Article>> = _bookmarks


    private val _bookmarkByUrl = MutableStateFlow<Article?>(null)
    val bookmarkByUrl: StateFlow<Article?> = _bookmarkByUrl

    init {
        observeBookmarks()
    }


    private fun observeBookmarks() {
        viewModelScope.launch {
            bookmarkRepository
                .getBookmarks()
                .collect { entities ->
                    _bookmarks.value = entities }
                }
        }


    fun observeBookmarkByUrl(url: String) {
        viewModelScope.launch {
            bookmarkRepository
                .getBookmarkByUrl(url)
                .collect { article ->
                    _bookmarkByUrl.value = article
                }
        }
    }


    fun addBookmark(article: Article) {
        viewModelScope.launch {
            bookmarkRepository.insertBookmark(article)
        }
    }

    fun removeBookmark(url: String) {
        viewModelScope.launch {
            bookmarkRepository.deleteBookmark(url)
        }
    }
}