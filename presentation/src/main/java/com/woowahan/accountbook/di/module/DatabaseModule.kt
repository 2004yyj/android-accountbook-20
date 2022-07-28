package com.woowahan.accountbook.di.module

import android.content.Context
import com.woowahan.accountbook.local.helper.DatabaseOpenHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val VERSION = 1
    private const val DB_NAME = "accountBook.db"

    @Provides
    @Singleton
    fun providesDatabaseOpenHelper(@ApplicationContext context: Context): DatabaseOpenHelper {
        return DatabaseOpenHelper(context, DB_NAME, VERSION)
    }
}