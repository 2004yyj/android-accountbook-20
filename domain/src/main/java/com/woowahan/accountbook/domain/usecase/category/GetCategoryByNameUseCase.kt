package com.woowahan.accountbook.domain.usecase.category

import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception

class GetCategoryByNameUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(name: String) = flow {
        emit(Result.Loading)
        try {
            val category = repository.getCategoryByName(name)
            emit(Result.Success(category))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}