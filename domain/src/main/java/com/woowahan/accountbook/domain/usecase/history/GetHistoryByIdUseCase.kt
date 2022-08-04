package com.woowahan.accountbook.domain.usecase.history

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.flow

class GetHistoryByIdUseCase(
    private val repository: HistoryRepository
) {
    operator fun invoke(id: Int) = flow {
        try {
            val history = repository.getHistoryById(id)
            emit(Result.Success(history))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}