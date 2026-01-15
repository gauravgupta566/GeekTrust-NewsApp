package com.greektrust.presentation.feed

import com.google.common.truth.Truth.assertThat
import com.greektrust.core.network.APIResult
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.dto.Source
import com.greektrust.presentation.common.MainDispatcherRule
import com.greektrust.presentation.search.FakeNewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsFeedViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: NewsFeedViewModel

    private fun article(id: Int) =
        Article(
            title = "Title $id",
            description = "Desc $id",
            url = "url$id",
            urlToImage = null,
            publishedAt = "",
            source = Source("","Source")
        )

    @Test
    fun `loadFirstPage emits loading then first page`() = runTest {
        val repo = FakeNewsRepository(
            pages = mapOf(
                1 to listOf(article(1), article(2))
            )
        )
        viewModel = NewsFeedViewModel(repo)

        viewModel.loadFirstPage()
        advanceUntilIdle()

        val state = viewModel.newFeed.value as APIResult.Success
        assertThat(state.data).hasSize(2)
        assertThat(viewModel.getCurrentPageNo()).isEqualTo(2)
    }

    @Test
    fun `loadNextPage appends data`() = runTest {
        val repo = FakeNewsRepository(
            pages = mapOf(
                1 to listOf(article(1), article(2)),
                2 to listOf(article(3))
            )
        )
        viewModel = NewsFeedViewModel(repo)

        viewModel.loadFirstPage()
        advanceUntilIdle()

        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.newFeed.value as APIResult.Success
        assertThat(state.data.map { it.title })
            .containsExactly("Title 1", "Title 2", "Title 3")
            .inOrder()

        assertThat(viewModel.getCurrentPageNo()).isEqualTo(3)
    }

    @Test
    fun `does not load next page when last page is reached`() = runTest {
        val repo = FakeNewsRepository(
            pages = mapOf(
                1 to listOf(article(1)),
                2 to emptyList()
            )
        )
        viewModel = NewsFeedViewModel(repo)

        viewModel.loadFirstPage()
        advanceUntilIdle()

        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.newFeed.value as APIResult.Success
        assertThat(state.data).hasSize(1)
        assertThat(viewModel.getCurrentPageNo()).isEqualTo(2)
    }

    @Test
    fun `loadNextPage ignored when already loading`() = runTest {
        val repo = FakeNewsRepository(
            pages = mapOf(
                1 to listOf(article(1))
            )
        )
        viewModel = NewsFeedViewModel(repo)

        viewModel.loadFirstPage()
        viewModel.loadNextPage() // should be ignored
        advanceUntilIdle()

        assertThat(viewModel.getCurrentPageNo()).isEqualTo(2)
    }
}