package com.woowahan.accountbook.domain.usecase.category

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class InsertCategoryUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(type: String, name: String, color: ULong) = flow {
        emit(Result.Loading)
        try {
            repository.insertCategory(type, name, color)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}