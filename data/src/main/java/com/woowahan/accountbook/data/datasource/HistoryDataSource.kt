package com.woowahan.accountbook.data.datasource

import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.domain.model.PaymentMethod

interface HistoryDataSource {
    suspend fun getTotalPayByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String): Long
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long): List<History>
    suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String): List<History>
    suspend fun createHistoryTable()
    suspend fun dropHistoryTable()
    suspend fun insertHistory(date: Long, amount: Long, content: String, category: Category, paymentMethod: PaymentMethod?)
    suspend fun updateHistory(id: Int, date: Long, amount: Long, content: String, category: Category, paymentMethod: PaymentMethod?)
    suspend fun deleteHistory(id: Int)
    suspend fun deleteAllHistory(idList: List<Int>)
}