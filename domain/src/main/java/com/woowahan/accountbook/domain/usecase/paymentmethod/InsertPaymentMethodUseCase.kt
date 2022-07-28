package com.woowahan.accountbook.domain.usecase.paymentmethod

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import com.woowahan.accountbook.domain.repository.PaymentMethodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class InsertPaymentMethodUseCase(
    private val repository: PaymentMethodRepository
) {
    operator fun invoke(name: String) = flow {
        emit(Result.Loading)
        try {
            repository.insertPaymentMethod(name)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}