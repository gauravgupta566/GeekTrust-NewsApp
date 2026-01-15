package com.greektrust.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _newsFeed = MutableStateFlow<APIResult<List<Article>>>(APIResult.Loading)
    val newFeed: StateFlow<APIResult<List<Article>>> = _newsFeed

    private val pageSize = 15

    private var currentPage = 1
    private var isLastPage = false
    private val allArticles = mutableListOf<Article>()
    private var isLoading = false
    private val accumulatedArticles = mutableListOf<Article>()


    fun getCurrentPageNo() = currentPage

    fun loadFirstPage() {
        currentPage = 1
        isLastPage = false
        allArticles.clear()
        loadPage()
    }

    fun loadNextPage() {
        if (isLoading || isLastPage) return
        loadPage()
    }

    private fun loadPage() {
        viewModelScope.launch {
            isLoading = true
            if(accumulatedArticles.isEmpty()){
                _newsFeed.value = APIResult.Loading
            }

            repository.getsNewsFeed(currentPage, pageSize).collect { result ->
                when (result) {
                    is APIResult.Success -> {
                        val newItems = result.data.orEmpty()

                        if (newItems.isEmpty()) {
                            isLastPage = true
                        } else {
                            accumulatedArticles.addAll(newItems)
                            currentPage++
                        }

                        _newsFeed.value =
                            APIResult.Success(accumulatedArticles.toList())
                    }

                    is APIResult.Error<*> -> {
                        _newsFeed.value = result
                    }

                    is APIResult.Loading -> {
                        if (currentPage == 1) {
                            _newsFeed.value = APIResult.Loading
                        }
                    }
                }
                isLoading = false
            }
        }
    }
}
