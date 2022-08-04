package com.woowahan.accountbook.domain.usecase.paymentmethod

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.CategoryRepository
import com.woowahan.accountbook.domain.repository.PaymentMethodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class UpdatePaymentMethodUseCase(
    private val repository: PaymentMethodRepository
) {
    operator fun invoke(id: Int, name: String) = flow {
        emit(Result.Loading)
        try {
            repository.updatePaymentMethod(id, name)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}