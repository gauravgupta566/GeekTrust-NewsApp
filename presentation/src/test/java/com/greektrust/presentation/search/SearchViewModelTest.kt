package com.greektrust.presentation.search

import com.google.common.truth.Truth.assertThat
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.dto.Source
import com.greektrust.presentation.common.MainDispatcherRule
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {


    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeNewsRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        fakeRepository = FakeNewsRepository()
        viewModel = SearchViewModel(fakeRepository)
    }

    @Test
    fun `does not search when query length is less than 2`() = runTest {
        viewModel.onQueryChange("a")

        advanceTimeBy(500)

        assertThat(fakeRepository.lastQuery).isNull()
    }

    @Test
    fun `search triggers after debounce`() = runTest {
        viewModel.onQueryChange("android")

        advanceTimeBy(399)
        assertThat(fakeRepository.lastQuery).isNull()

        advanceTimeBy(1)
        assertThat(fakeRepository.lastQuery).isEqualTo("android")
    }

    @Test
    fun `updates data when repository emits result`() = runTest {
        val article = Article(
            title = "Kotlin",
            description = "Test",
            url = "url",
            urlToImage = null,
            publishedAt = "",
            source = Source("","Test")
        )

        viewModel.onQueryChange("kotlin")
        advanceTimeBy(500)

        fakeRepository.searchFlow.emit(APIResult.Success(listOf(article)))

        assertThat(viewModel.data.value)
            .isEqualTo(APIResult.Success(listOf(article)))
    }

    @Test
    fun `flatMapLatest emits only latest result`() = runTest {
        viewModel.onQueryChange("kot")
        advanceTimeBy(500)

        fakeRepository.searchFlow.emit(
            APIResult.Success(listOf(Article(
                title = "Old",
                source = Source(null,""),
                author = "",
                description = "",
                url = "",
                urlToImage = "",
                publishedAt = "",
                content = ""
            )))
        )

        viewModel.onQueryChange("kotlin")
        advanceTimeBy(500)

        fakeRepository.searchFlow.emit(
            APIResult.Success(listOf(Article(
                title = "New",
                source = Source("","cdc"),
                url = "https://abcd.com",
                publishedAt = ""
            )))
        )

        val result = viewModel.data.value as APIResult.Success
        assertThat(result.data.first().title).isEqualTo("New")
    }
}