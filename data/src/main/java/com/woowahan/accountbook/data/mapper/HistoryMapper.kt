package com.woowahan.accountbook.data.mapper

import com.woowahan.accountbook.data.entity.HistoryData
import com.woowahan.accountbook.domain.model.History

fun HistoryData.toModel(): History {
    return History(
        id,
        date,
        amount,
        content,
        category.toModel(),
        paymentMethod.toModel()
    )
}

fun History.toEntity(): HistoryData {
    return HistoryData(
        id,
        date,
        amount,
        content,
        category.toEntity(),
        paymentMethod.toEntity()
    )
}