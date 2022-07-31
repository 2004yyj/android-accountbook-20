package com.woowahan.accountbook.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.getBackMonthMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.clear(Calendar.MINUTE)
    calendar.clear(Calendar.SECOND)
    calendar.clear(Calendar.MILLISECOND)
    calendar.add(Calendar.MONTH, -1)
    return calendar.timeInMillis
}

fun Long.getCurrentMonthFirstDayMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.clear(Calendar.MINUTE)
    calendar.clear(Calendar.SECOND)
    calendar.clear(Calendar.MILLISECOND)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return calendar.timeInMillis
}

fun Long.getCurrentDateMidNightMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.clear(Calendar.MINUTE)
    calendar.clear(Calendar.SECOND)
    calendar.clear(Calendar.MILLISECOND)
    return calendar.timeInMillis
}


fun Long.getCurrentDateMillis(date: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.clear(Calendar.MINUTE)
    calendar.clear(Calendar.SECOND)
    calendar.clear(Calendar.MILLISECOND)
    calendar.set(Calendar.DAY_OF_MONTH, date)
    return calendar.timeInMillis
}

fun Long.getForwardMonthMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.clear(Calendar.MINUTE)
    calendar.clear(Calendar.SECOND)
    calendar.clear(Calendar.MILLISECOND)
    calendar.add(Calendar.MONTH, 1)
    return calendar.timeInMillis
}

fun String.toLongTime(format: String): Long {
    val sdf = SimpleDateFormat(format, Locale.KOREA)
    return sdf.parse(this)?.time ?: 0L
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