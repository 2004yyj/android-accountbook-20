package com.woowahan.accountbook.domain.usecase.category

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class UpdateCategoryUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(id: Int, type: String, name: String, color: Long) = flow {
        emit(Result.Loading)
        try {
            repository.updateCategory(id, type, name, color)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}