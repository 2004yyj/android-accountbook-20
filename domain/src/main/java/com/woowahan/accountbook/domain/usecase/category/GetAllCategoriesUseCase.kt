package com.woowahan.accountbook.domain.usecase.category

import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class GetAllCategoriesUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            val categories = repository.getAllCategories()
            emit(Result.Success(categories))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}