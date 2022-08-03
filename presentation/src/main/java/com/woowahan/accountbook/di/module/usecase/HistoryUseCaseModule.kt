package com.woowahan.accountbook.di.module.usecase

import com.woowahan.accountbook.domain.repository.HistoryRepository
import com.woowahan.accountbook.domain.usecase.history.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryUseCaseModule {
    @Provides
    @Singleton
    fun providesCreateHistoryTableUseCase(repository: HistoryRepository): CreateHistoryTableUseCase {
        return CreateHistoryTableUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDeleteAllHistoryUseCase(repository: HistoryRepository): DeleteAllHistoryUseCase {
        return DeleteAllHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDeleteHistoryUseCase(repository: HistoryRepository): DeleteHistoryUseCase {
        return DeleteHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllHistoriesByMonthAndTypeUseCase(repository: HistoryRepository): GetAllHistoriesByMonthAndTypeUseCase {
        return GetAllHistoriesByMonthAndTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetTotalPayByMonthAndTypeUseCase(repository: HistoryRepository): GetTotalPayByMonthAndTypeUseCase {
        return GetTotalPayByMonthAndTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesInsertHistoryUseCase(repository: HistoryRepository): InsertHistoryUseCase {
        return InsertHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesUpdateHistoryUseCase(repository: HistoryRepository): UpdateHistoryUseCase {
        return UpdateHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllStatisticsByCategoryTypeUseCase(repository: HistoryRepository): GetAllStatisticsByCategoryTypeUseCase {
        return GetAllStatisticsByCategoryTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetTotalListByCategoryNameGroupByDateUseCase(repository: HistoryRepository): GetTotalListByCategoryNameGroupByDateUseCase {
        return GetTotalListByCategoryNameGroupByDateUseCase(repository)
    }
}