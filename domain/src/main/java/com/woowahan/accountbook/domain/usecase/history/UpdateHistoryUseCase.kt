package com.woowahan.accountbook.domain.usecase.history

import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class UpdateHistoryUseCase(
    private val repository: HistoryRepository
) {
    operator fun invoke(id: Int, date: Long, amount: Int, content: String, category: Category, paymentMethod: PaymentMethod?) = flow {
        try {
            repository.updateHistory(id, date, amount, content, category, paymentMethod)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}