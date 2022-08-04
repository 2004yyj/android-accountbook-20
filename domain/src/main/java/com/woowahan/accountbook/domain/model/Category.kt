package com.woowahan.accountbook.domain.model

data class Category(
    val id: Int,
    val type: PaymentType,
    val name: String,
    val color: ULong
)