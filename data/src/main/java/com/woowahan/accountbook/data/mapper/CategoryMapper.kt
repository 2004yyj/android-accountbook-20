package com.woowahan.accountbook.data.mapper

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentType

fun CategoryData.toModel(): Category {
    return Category(
        id, PaymentType.valueOf(type), name, color
    )
}

fun Category.toEntity(): CategoryData {
    return CategoryData(
        id, type.toString(), name, color
    )
}