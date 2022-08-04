package com.woowahan.accountbook.domain.model

data class History(
    val id: Int,
    val date: Long,
    val amount: Long,
    val content: String,
    val incomeTotalByDate: Long,
    val expenseTotalByDate: Long,
    val category: Category,
    val paymentMethod: PaymentMethod?,
    var isChecked: Boolean = false
)