package com.woowahan.accountbook.domain.model

data class Statistic(
    val categoryName: String,
    val categoryTotal: Long,
    val categoryPercent: Float,
    val categoryColor: ULong
)
