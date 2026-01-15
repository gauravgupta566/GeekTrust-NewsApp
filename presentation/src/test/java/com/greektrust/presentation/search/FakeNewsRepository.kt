package com.greektrust.presentation.search

import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class FakeNewsRepository(
    private val pages: Map<Int, List<Article>> = emptyMap()
) : NewsRepository {

    val searchFlow = MutableSharedFlow<APIResult<List<Article>>>()

    var lastQuery: String? = null

    override fun getsSearchNews(query: String): Flow<APIResult<List<Article>>> {
        lastQuery = query
        return searchFlow
    }

    // unused in this test
    override fun getsNewsFeed(page: Int, pageSize: Int)
            : Flow<APIResult<List<Article>>> {

        val list = mutableListOf<Article>()

        return flow { emit(APIResult.Success(list.orEmpty())) }
    }


}