package com.woowahan.accountbook.data.mapper

import com.woowahan.accountbook.data.entity.TotalData
import com.woowahan.accountbook.domain.model.Total

fun TotalData.toModel(): Total {
    return Total(
        total, date
    )
}

fun Total.toEntity(): TotalData {
    return TotalData(
        total, date
    )
}