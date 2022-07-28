package com.woowahan.accountbook.data.datasource

import com.woowahan.accountbook.domain.model.PaymentMethod

interface PaymentMethodDataSource {
    suspend fun getAllPaymentMethods(): List<PaymentMethod>
    suspend fun getPaymentMethodByName(name: String): PaymentMethod
    suspend fun createPaymentMethodTable()
    suspend fun dropPaymentMethodTable()
    suspend fun insertPaymentMethod(name: String)
    suspend fun updatePaymentMethod(id: Int, name: String)
    suspend fun deletePaymentMethod(id: Int)
}