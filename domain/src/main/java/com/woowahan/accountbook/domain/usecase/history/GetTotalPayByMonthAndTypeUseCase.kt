package com.woowahan.accountbook.domain.usecase.history

import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class GetTotalPayByMonthAndTypeUseCase(
    private val repository: HistoryRepository
) {
    operator fun invoke(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentType) = flow {
        try {
            val total = repository.getTotalPayByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type)
            emit(Result.Success(total))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}