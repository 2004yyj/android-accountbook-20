package com.woowahan.accountbook.domain.usecase.paymentmethod

import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.repository.PaymentMethodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPaymentMethodByIdUseCase(
    private val repository: PaymentMethodRepository
) {
    operator fun invoke(id: Int) = flow {
        emit(Result.Loading)
        try {
            val paymentMethod = repository.getPaymentMethodById(id)
            emit(Result.Success(paymentMethod))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}