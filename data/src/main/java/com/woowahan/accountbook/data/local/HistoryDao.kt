package com.woowahan.accountbook.data.local

import com.woowahan.accountbook.data.entity.*
import com.woowahan.accountbook.domain.model.History

interface HistoryDao {
    suspend fun getTotalPayByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentTypeData): Long
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long): List<HistoryData>
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentTypeData): List<HistoryData>
    suspend fun createHistoryTable()
    suspend fun dropHistoryTable()
    suspend fun insertHistory(date: Long, amount: Long, content: String, category: CategoryData, paymentMethod: PaymentMethodData?)
    suspend fun updateHistory(id: Int, date: Long, amount: Long, content: String, category: CategoryData, paymentMethod: PaymentMethodData?)
    suspend fun deleteHistory(id: Int)
    suspend fun deleteAllHistory(idList: List<Int>)
    suspend fun getAllStatisticsByCategoryType(firstDayOfMonth: Long, firstDayOfNextMonth: Long,): List<StatisticData>
    suspend fun getTotalListByCategoryNameGroupByDate(name: String): List<TotalData>
    suspend fun getAllExpendHistoriesByMonthAndCategoryName(firstDayOfMonth: Long, firstDayOfNextMonth: Long, name: String): List<HistoryData>
    suspend fun getHistoryById(id: Int): HistoryData
}