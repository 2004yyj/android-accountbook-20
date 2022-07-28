package com.woowahan.accountbook.di.module

import com.woowahan.accountbook.data.repository.CategoryRepositoryImpl
import com.woowahan.accountbook.data.repository.HistoryRepositoryImpl
import com.woowahan.accountbook.data.repository.PaymentMethodRepositoryImpl
import com.woowahan.accountbook.domain.repository.CategoryRepository
import com.woowahan.accountbook.domain.repository.HistoryRepository
import com.woowahan.accountbook.domain.repository.PaymentMethodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindsPaymentMethodRepository(paymentMethodRepositoryImpl: PaymentMethodRepositoryImpl): PaymentMethodRepository

    @Binds
    abstract fun bindsHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository
}