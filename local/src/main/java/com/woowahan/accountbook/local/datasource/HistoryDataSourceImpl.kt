package com.woowahan.accountbook.local.datasource

import com.woowahan.accountbook.data.datasource.HistoryDataSource
import com.woowahan.accountbook.data.local.HistoryDao
import com.woowahan.accountbook.data.mapper.toEntity
import com.woowahan.accountbook.data.mapper.toModel
import com.woowahan.accountbook.domain.model.*
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val dao: HistoryDao
): HistoryDataSource {
    override suspend fun getTotalPayByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long,
        type: PaymentType
    ): Long {
        return dao.getTotalPayByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type.toEntity())
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
        type: PaymentType
    ): List<History> {
        return dao.getAllHistoriesByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type.toEntity()).map {
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
        amount: Long,
        content: String,
        category: Category,
        paymentMethod: PaymentMethod?
    ) {
        return dao.insertHistory(date, amount, content, category.toEntity(), paymentMethod?.toEntity())
    }

    override suspend fun updateHistory(
        id: Int,
        date: Long,
        amount: Long,
        content: String,
        category: Category,
        paymentMethod: PaymentMethod?
    ) {
        return dao.updateHistory(id, date, amount, content, category.toEntity(), paymentMethod?.toEntity())
    }

    override suspend fun deleteHistory(id: Int) {
        return dao.deleteHistory(id)
    }

    override suspend fun deleteAllHistory(idList: List<Int>) {
        return dao.deleteAllHistory(idList)
    }

    override suspend fun getAllStatisticsByCategoryType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long
    ): List<Statistic> {
        return dao.getAllStatisticsByCategoryType(firstDayOfMonth, firstDayOfNextMonth).map { it.toModel() }
    }
}