package com.woowahan.accountbook.domain.repository

import com.woowahan.accountbook.domain.model.*

interface HistoryRepository {
    suspend fun getTotalPayByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentType): Long
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long): List<History>
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentType): List<History>
    suspend fun createHistoryTable()
    suspend fun dropHistoryTable()
    suspend fun insertHistory(date: Long, amount: Long, content: String, category: Category, paymentMethod: PaymentMethod?)
    suspend fun updateHistory(id: Int, date: Long, amount: Long, content: String, category: Category, paymentMethod: PaymentMethod?)
    suspend fun deleteHistory(id: Int)
    suspend fun deleteAllHistory(idList: List<Int>)
    suspend fun getAllStatisticsByCategoryType(firstDayOfMonth: Long, firstDayOfNextMonth: Long): List<Statistic>
}