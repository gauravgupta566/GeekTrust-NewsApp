package com.greektrust.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private val _query = MutableStateFlow("")
    private val _data = MutableStateFlow<APIResult<List<Article>>>(APIResult.Loading)
    val data: StateFlow<APIResult<List<Article>>> = _data

    init {
        observeSearchQuery()
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }

    /** Core search pipeline */
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        _query
            .debounce(400)
            .distinctUntilChanged()
            .filter { it.length >= 2 }
            .flatMapLatest { query ->
                newsRepository.getsSearchNews(query)
            }
            .onEach { result ->
                _data.value = result
            }
            .launchIn(viewModelScope)
    }


}