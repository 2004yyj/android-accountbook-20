package com.woowahan.accountbook.data.local

import com.woowahan.accountbook.data.entity.PaymentMethodData

interface PaymentMethodDao {
    suspend fun getAllPaymentMethods(): List<PaymentMethodData>
    suspend fun getPaymentMethodByName(name: String): PaymentMethodData
    suspend fun getPaymentMethodById(id: Int): PaymentMethodData
    suspend fun createPaymentMethodTable()
    suspend fun dropPaymentMethodTable()
    suspend fun insertPaymentMethod(name: String)
    suspend fun updatePaymentMethod(id: Int, name: String)
    suspend fun deletePaymentMethod(id: Int)
}