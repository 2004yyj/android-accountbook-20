package com.woowahan.accountbook.di.module

import com.woowahan.accountbook.data.local.dao.CategoryDao
import com.woowahan.accountbook.data.local.dao.PaymentMethodDao
import com.woowahan.accountbook.local.dao.CategoryDaoImpl
import com.woowahan.accountbook.local.dao.PaymentMethodDaoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {
    @Binds
    abstract fun bindsCategoryDao(categoryDaoImpl: CategoryDaoImpl): CategoryDao

    @Binds
    abstract fun bindsPaymentMethodDao(paymentMethodDaoImpl: PaymentMethodDaoImpl): PaymentMethodDao
}