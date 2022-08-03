package com.woowahan.accountbook.domain.usecase.history

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.flow

class GetTotalListByCategoryNameGroupByDateUseCase(
    private val repository: HistoryRepository
) {
    operator fun invoke(name: String) = flow {
        try {
            val totalList = repository.getTotalListByCategoryNameGroupByDate(name)
            emit(Result.Success(totalList))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}