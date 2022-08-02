package com.woowahan.accountbook.domain.usecase.category

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class GetCategoryByIdUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(id: Int) = flow {
        emit(Result.Loading)
        try {
            val category = repository.getCategoryById(id)
            emit(Result.Success(category))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}