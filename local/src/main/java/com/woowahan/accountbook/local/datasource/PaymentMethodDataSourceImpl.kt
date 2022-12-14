package com.woowahan.accountbook.local.datasource

import com.woowahan.accountbook.data.datasource.PaymentMethodDataSource
import com.woowahan.accountbook.data.local.PaymentMethodDao
import com.woowahan.accountbook.data.mapper.toModel
import com.woowahan.accountbook.domain.model.PaymentMethod
import javax.inject.Inject

class PaymentMethodDataSourceImpl @Inject constructor(
    private val dao: PaymentMethodDao
): PaymentMethodDataSource {
    override suspend fun getAllPaymentMethods(): List<PaymentMethod> {
        return dao.getAllPaymentMethods().map { it.toModel() }
    }

    override suspend fun getPaymentMethodByName(name: String): PaymentMethod {
        return dao.getPaymentMethodByName(name).toModel()
    }

    override suspend fun getPaymentMethodById(id: Int): PaymentMethod {
        return dao.getPaymentMethodById(id).toModel()
    }

    override suspend fun insertPaymentMethod(name: String) {
        return dao.insertPaymentMethod(name)
    }

    override suspend fun updatePaymentMethod(id: Int, name: String) {
        return dao.updatePaymentMethod(id, name)
    }

    override suspend fun deletePaymentMethod(id: Int) {
        return dao.deletePaymentMethod(id)
    }
}