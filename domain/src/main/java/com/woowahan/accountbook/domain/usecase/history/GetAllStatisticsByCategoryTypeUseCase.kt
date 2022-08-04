package com.woowahan.accountbook.domain.usecase.history

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class GetAllStatisticsByCategoryTypeUseCase(
    private val repository: HistoryRepository
) {
    operator fun invoke(firstDayOfMonth: Long, firstDayOfNextMonth: Long) = flow {
        try {
            val statistics = repository.getAllStatisticsByCategoryType(firstDayOfMonth, firstDayOfNextMonth)
            emit(Result.Success(statistics))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}