package com.woowahan.accountbook.data.mapper

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.domain.model.Category

fun CategoryData.toModel(): Category {
    return Category(
        id, type, name, color
    )
}

fun Category.toEntity(): CategoryData {
    return CategoryData(
        id, type, name, color
    )
}