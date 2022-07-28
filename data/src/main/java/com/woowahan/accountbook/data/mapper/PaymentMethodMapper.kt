package com.woowahan.accountbook.data.mapper

import com.woowahan.accountbook.data.entity.PaymentMethodData
import com.woowahan.accountbook.domain.model.PaymentMethod

fun PaymentMethodData.toModel(): PaymentMethod {
    return PaymentMethod(
        id, name
    )
}

fun PaymentMethod.toEntity(): PaymentMethodData {
    return PaymentMethodData(
        id, name
    )
}