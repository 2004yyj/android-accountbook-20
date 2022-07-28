package com.woowahan.accountbook.domain.repository

import com.woowahan.accountbook.domain.model.PaymentMethod

interface PaymentMethodRepository {
    suspend fun getAllPaymentMethods(): List<PaymentMethod>
    suspend fun getPaymentMethodByName(name: String): PaymentMethod
    suspend fun createPaymentMethodTable()
    suspend fun dropPaymentMethodTable()
    suspend fun insertPaymentMethod(name: String)
    suspend fun updatePaymentMethod(id: Int, name: String)
    suspend fun deletePaymentMethod(id: Int)
}