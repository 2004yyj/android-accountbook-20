package com.woowahan.accountbook.data.repository

import com.woowahan.accountbook.data.datasource.HistoryDataSource
import com.woowahan.accountbook.domain.model.*
import com.woowahan.accountbook.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dataSource: HistoryDataSource
): HistoryRepository {
    override suspend fun getTotalPayByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long,
        type: PaymentType
    ): Long {
        return dataSource.getTotalPayByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type)
    }

    override suspend fun getAllHistoriesByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long
    ): List<History> {
        return dataSource.getAllHistoriesByMonthAndType(firstDayOfMonth, firstDayOfNextMonth)
    }

    override suspend fun getAllHistoriesByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long,
        type: PaymentType
    ): List<History> {
        return dataSource.getAllHistoriesByMonthAndType(firstDayOfMonth, firstDayOfNextMonth, type)
    }

    override suspend fun createHistoryTable() {
        return dataSource.createHistoryTable()
    }

    override suspend fun dropHistoryTable() {
        return dataSource.dropHistoryTable()
    }

    override suspend fun insertHistory(
        date: Long,
        amount: Long,
        content: String,
        category: Category,
        paymentMethod: PaymentMethod?
    ) {
        return dataSource.insertHistory(date, amount, content, category, paymentMethod)
    }

    override suspend fun updateHistory(
        id: Int,
        date: Long,
        amount: Long,
        content: String,
        category: Category,
        paymentMethod: PaymentMethod?
    ) {
        return dataSource.updateHistory(id, date, amount, content, category, paymentMethod)
    }

    override suspend fun deleteHistory(id: Int) {
        return dataSource.deleteHistory(id)
    }

    override suspend fun deleteAllHistory(idList: List<Int>) {
        return dataSource.deleteAllHistory(idList)
    }

    override suspend fun getAllStatisticsByCategoryType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long
    ): List<Statistic> {
        return dataSource.getAllStatisticsByCategoryType(firstDayOfMonth, firstDayOfNextMonth)
    }
}