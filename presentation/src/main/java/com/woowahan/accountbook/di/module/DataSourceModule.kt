package com.woowahan.accountbook.di.module

import com.woowahan.accountbook.data.datasource.CategoryDataSource
import com.woowahan.accountbook.local.datasource.CategoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindsCategoryDataSource(categoryDataSourceImpl: CategoryDataSourceImpl): CategoryDataSource
}