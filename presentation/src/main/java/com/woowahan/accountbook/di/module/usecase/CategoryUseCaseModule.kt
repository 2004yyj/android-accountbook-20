package com.woowahan.accountbook.di.module.usecase

import com.woowahan.accountbook.domain.repository.CategoryRepository
import com.woowahan.accountbook.domain.usecase.category.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryUseCaseModule {
    @Provides
    @Singleton
    fun providesCreateCategoryTableUseCase(repository: CategoryRepository): CreateCategoryTableUseCase {
        return CreateCategoryTableUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDeleteCategoryUseCase(repository: CategoryRepository): DeleteCategoryUseCase {
        return DeleteCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllCategoriesUseCase(repository: CategoryRepository): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllCategoryByTypeUseCase(repository: CategoryRepository): GetAllCategoryByTypeUseCase {
        return GetAllCategoryByTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetCategoryByNameUseCase(repository: CategoryRepository): GetCategoryByNameUseCase {
        return GetCategoryByNameUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetCategoryByIdUseCase(repository: CategoryRepository): GetCategoryByIdUseCase {
        return GetCategoryByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesInsertCategoryUseCase(repository: CategoryRepository): InsertCategoryUseCase {
        return InsertCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesUpdateCategoryUseCase(repository: CategoryRepository): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(repository)
    }
}