package com.woowahan.accountbook.data.mapper

import com.woowahan.accountbook.data.entity.StatisticData
import com.woowahan.accountbook.domain.model.Statistic

fun StatisticData.toModel(): Statistic {
    return Statistic(
        categoryName,
        categoryTotal,
        categoryPercent,
        categoryColor
    )
}

fun Statistic.toEntity(): StatisticData {
    return StatisticData(
        categoryName,
        categoryTotal,
        categoryPercent,
        categoryColor
    )
}