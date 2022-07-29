package com.woowahan.accountbook.data.entity

data class HistoryData(
    val id: Int,
    val date: Long,
    val amount: Long,
    val content: String,
    val category: CategoryData,
    val paymentMethod: PaymentMethodData?,
)