package com.woowahan.accountbook.di.module.usecase

import com.woowahan.accountbook.domain.repository.PaymentMethodRepository
import com.woowahan.accountbook.domain.usecase.paymentmethod.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentMethodUseCaseModule {
    @Provides
    @Singleton
    fun providesCreatePaymentMethodTableUseCase(repository: PaymentMethodRepository): CreatePaymentMethodTableUseCase {
        return CreatePaymentMethodTableUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDeletePaymentMethodUseCase(repository: PaymentMethodRepository): DeletePaymentMethodUseCase {
        return DeletePaymentMethodUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllPaymentMethodsUseCase(repository: PaymentMethodRepository): GetAllPaymentMethodsUseCase {
        return GetAllPaymentMethodsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetPaymentMethodByNameUseCase(repository: PaymentMethodRepository): GetPaymentMethodByNameUseCase {
        return GetPaymentMethodByNameUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetPaymentMethodByIdUseCase(repository: PaymentMethodRepository): GetPaymentMethodByIdUseCase {
        return GetPaymentMethodByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesInsertPaymentMethodUseCase(repository: PaymentMethodRepository): InsertPaymentMethodUseCase {
        return InsertPaymentMethodUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesUpdatePaymentMethodUseCase(repository: PaymentMethodRepository): UpdatePaymentMethodUseCase {
        return UpdatePaymentMethodUseCase(repository)
    }
}