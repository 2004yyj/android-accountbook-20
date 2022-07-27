package com.woowahan.accountbook.util

import java.text.DecimalFormat

fun Int.toMoneyString(): String {
    return DecimalFormat("###,###").format(this)
}

fun String.parseMoneyInt(): Int {
    return (DecimalFormat("###,###").parse(this) ?: 0).toInt()
}

fun Long.toMoneyString(): String {
    return DecimalFormat("###,###").format(this)
}

fun String.parseMoneyLong(): Long {
    return (DecimalFormat("###,###").parse(this) ?: 0).toLong()
}