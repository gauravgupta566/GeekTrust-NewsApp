package com.greektrust.presentation.bookmark

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.greektrust.data.model.dto.Article
import com.greektrust.data.model.dto.Source
import com.greektrust.presentation.common.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookMarkViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakeBookmarkRepository
    private lateinit var viewModel: BookMarkViewModel

    private fun article(id: Int) =
        Article(
            title = "Title $id",
            description = "Desc $id",
            url = "url$id",
            urlToImage = null,
            publishedAt = "",
            source = Source(null, "Source")
        )

    @Before
    fun setup() {
        repository = FakeBookmarkRepository()
        viewModel = BookMarkViewModel(repository)
    }

    @Test
    fun `bookmarks emits empty list initially`() = runTest {
        viewModel.bookmarks.test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isBookmarked emits false when not bookmarked`() = runTest {
        viewModel.isBookmarked("url1").test {
            assertThat(awaitItem()).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleBookmark inserts article when not bookmarked`() = runTest {
        val article = article(1)

        viewModel.toggleBookmark(article)
        advanceUntilIdle()

        viewModel.bookmarks.test {
            assertThat(awaitItem()).contains(article)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.bookmarkEvent.test {
            assertThat(awaitItem()).isEqualTo(BookmarkEvent.Added)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleBookmark removes article when already bookmarked`() = runTest {
        val article = article(1)

        repository.insertBookmark(article)

        viewModel.toggleBookmark(article)
        advanceUntilIdle()

        viewModel.bookmarks.test {
            assertThat(awaitItem()).doesNotContain(article)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.bookmarkEvent.test {
            assertThat(awaitItem()).isEqualTo(BookmarkEvent.Removed)
            cancelAndIgnoreRemainingEvents()
        }
    }
}