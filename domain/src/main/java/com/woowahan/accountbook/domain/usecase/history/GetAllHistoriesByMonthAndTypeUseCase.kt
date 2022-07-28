package com.woowahan.accountbook.domain.usecase.history

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class GetAllHistoriesByMonthAndTypeUseCase(
    private val repository: HistoryRepository
) {
    operator fun invoke(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String = "") = flow {
        try {
            val list = if (type.isEmpty()) {
                repository.getAllHistoriesByMonthAndType(firstDayOfMonth, firstDayOfNextMonth)
            } else {
                repository.getAllHistoriesByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type)
            }
            emit(Result.Success(list))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}