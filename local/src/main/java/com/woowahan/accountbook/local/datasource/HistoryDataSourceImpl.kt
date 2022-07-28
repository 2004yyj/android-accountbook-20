package com.woowahan.accountbook.local.datasource

import com.woowahan.accountbook.data.datasource.HistoryDataSource
import com.woowahan.accountbook.data.local.HistoryDao
import com.woowahan.accountbook.data.mapper.toEntity
import com.woowahan.accountbook.data.mapper.toModel
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.domain.model.PaymentMethod
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val dao: HistoryDao
): HistoryDataSource {
    override suspend fun getTotalPayByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long,
        type: String
    ): Long {
        return dao.getTotalPayByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type)
    }

    override suspend fun getAllHistoriesByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long
    ): List<History> {
        return dao.getAllHistoriesByMonthAndType(firstDayOfMonth, firstDayOfNextMonth).map {
            it.toModel()
        }
    }

    override suspend fun getAllHistoriesByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long,
        type: String
    ): List<History> {
        return dao.getAllHistoriesByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type).map {
            it.toModel()
        }
    }

    override suspend fun createHistoryTable() {
        return dao.createHistoryTable()
    }

    override suspend fun dropHistoryTable() {
        return dao.dropHistoryTable()
    }

    override suspend fun insertHistory(
        date: Long,
        amount: Int,
        content: String,
        category: Category,
        paymentMethod: PaymentMethod?
    ) {
        return dao.insertHistory(date, amount, content, category.toEntity(), paymentMethod?.toEntity())
    }

    override suspend fun updateHistory(
        id: Int,
        date: Long,
        amount: Int,
        content: String,
        category: Category,
        paymentMethod: PaymentMethod?
    ) {
        return dao.updateHistory(id, date, amount, content, category.toEntity(), paymentMethod?.toEntity())
    }

    override suspend fun deleteHistory(id: Int) {
        return dao.deleteHistory(id)
    }
}