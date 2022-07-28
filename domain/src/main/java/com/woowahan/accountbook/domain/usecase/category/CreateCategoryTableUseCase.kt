package com.woowahan.accountbook.domain.usecase.category

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class CreateCategoryTableUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            repository.createCategoryTable()
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}