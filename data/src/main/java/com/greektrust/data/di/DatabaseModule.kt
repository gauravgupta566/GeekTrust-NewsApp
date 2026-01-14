package com.greektrust.data.di

import android.content.Context
import androidx.room.Room
import com.greektrust.data.datasource.local.NewsDatabase
import com.greektrust.data.datasource.local.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        ).build()

    @Provides
    fun provideBookmarkDao(db: NewsDatabase): BookmarkDao =
        db.bookmarkDao()


}
