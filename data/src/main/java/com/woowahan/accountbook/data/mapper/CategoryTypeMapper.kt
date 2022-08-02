package com.woowahan.accountbook.data.mapper

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.data.entity.PaymentTypeData
import com.woowahan.accountbook.domain.model.PaymentType

fun PaymentTypeData.toModel(): PaymentType {
    return PaymentType.valueOf(this.toString())
}

fun PaymentType.toEntity(): PaymentTypeData {
    return PaymentTypeData.valueOf(this.toString())
}