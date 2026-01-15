package com.greektrust.domain.usecases

import com.greektrust.data.repository.BookmarkRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(private val bookmarkRepository: BookmarkRepository) {


}