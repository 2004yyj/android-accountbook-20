package com.woowahan.accountbook.data.local

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.data.entity.HistoryData
import com.woowahan.accountbook.data.entity.PaymentMethodData

interface HistoryDao {
    suspend fun getTotalPayByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String): Long
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long): List<HistoryData>
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String): List<HistoryData>
    suspend fun createHistoryTable()
    suspend fun dropHistoryTable()
    suspend fun insertHistory(date: Long, amount: Int, content: String, category: CategoryData, paymentMethod: PaymentMethodData?)
    suspend fun updateHistory(id: Int, date: Long, amount: Int, content: String, category: CategoryData, paymentMethod: PaymentMethodData?)
    suspend fun deleteHistory(id: Int)
}