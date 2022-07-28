package com.woowahan.accountbook.domain.model

data class History(
    val id: Int,
    val date: Long,
    val amount: Int,
    val content: String,
    val category: Category,
    val paymentMethod: PaymentMethod?,
)