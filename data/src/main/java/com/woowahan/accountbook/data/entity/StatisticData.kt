package com.woowahan.accountbook.data.entity

data class StatisticData(
    val categoryName: String,
    val categoryTotal: Long,
    val categoryPercent: Float,
    val categoryColor: ULong
)