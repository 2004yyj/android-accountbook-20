package com.woowahan.accountbook.di.module

import com.woowahan.accountbook.data.local.CategoryDao
import com.woowahan.accountbook.data.local.HistoryDao
import com.woowahan.accountbook.data.local.PaymentMethodDao
import com.woowahan.accountbook.local.dao.CategoryDaoImpl
import com.woowahan.accountbook.local.dao.HistoryDaoImpl
import com.woowahan.accountbook.local.dao.PaymentMethodDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {
    @Binds
    abstract fun bindsCategoryDao(categoryDaoImpl: CategoryDaoImpl): CategoryDao

    @Binds
    abstract fun bindsPaymentMethodDao(paymentMethodDaoImpl: PaymentMethodDaoImpl): PaymentMethodDao

    @Binds
    abstract fun bindsHistoryDao(historyDaoImpl: HistoryDaoImpl): HistoryDao
}