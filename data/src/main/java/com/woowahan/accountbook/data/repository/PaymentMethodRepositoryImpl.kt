package com.woowahan.accountbook.data.repository

import com.woowahan.accountbook.data.datasource.PaymentMethodDataSource
import com.woowahan.accountbook.data.mapper.toModel
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.repository.PaymentMethodRepository
import javax.inject.Inject

class PaymentMethodRepositoryImpl @Inject constructor(
    private val dataSource: PaymentMethodDataSource
): PaymentMethodRepository {
    override suspend fun getAllPaymentMethods(): List<PaymentMethod> {
        return dataSource.getAllPaymentMethods()
    }

    override suspend fun getPaymentMethodByName(name: String): PaymentMethod {
        return dataSource.getPaymentMethodByName(name)
    }

    override suspend fun getPaymentMethodById(id: Int): PaymentMethod {
        return dataSource.getPaymentMethodById(id)
    }

    override suspend fun insertPaymentMethod(name: String) {
        return dataSource.insertPaymentMethod(name)
    }

    override suspend fun updatePaymentMethod(id: Int, name: String) {
        return dataSource.updatePaymentMethod(id, name)
    }

    override suspend fun deletePaymentMethod(id: Int) {
        return dataSource.deletePaymentMethod(id)
    }
}