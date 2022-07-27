package com.woowahan.accountbook.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.getBackMonthMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.add(Calendar.MONTH, -1)
    return calendar.timeInMillis
}

fun Long.getForwardMonthMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.add(Calendar.MONTH, 1)
    return calendar.timeInMillis
}

fun Long.toYearMonth(): String {
    val sdf = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
    return sdf.format(this) ?: ""
}

fun String.toMillis(): Long {
    val sdf = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
    return sdf.parse(this)?.time ?: 0L
}

fun Long.toMonthDate(): String {
    val sdf = SimpleDateFormat("M월 dd일", Locale.KOREA)
    return sdf.format(this) ?: ""
}

fun Long.toYearMonthDayDots(): String {
    val sdf = SimpleDateFormat("yyyy. M. dd EEEE", Locale.KOREA)
    return sdf.format(this) ?: ""
}