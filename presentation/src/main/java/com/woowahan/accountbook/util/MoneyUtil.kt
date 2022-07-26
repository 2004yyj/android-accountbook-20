package com.woowahan.accountbook.util

import java.text.DecimalFormat

fun Int.toMoneyString(): String {
    return DecimalFormat("###,###").format(this)
}