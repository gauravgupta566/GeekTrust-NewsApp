package com.greektrust.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.ApiStatus
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _newsFeed = MutableStateFlow<APIResult<List<Article>>>(APIResult.Loading)
    val newFeed: StateFlow<APIResult<List<Article>>> = _newsFeed

    fun getNewsFeed(){
        viewModelScope.launch {
            _newsFeed.value = APIResult.Loading
            repository.getsNewsFeed().collect {it->
                _newsFeed.value = it

            }
        }

    }
}
