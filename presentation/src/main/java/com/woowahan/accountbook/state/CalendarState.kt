package com.woowahan.accountbook.state

import androidx.compose.runtime.*
import java.util.*

@Stable
class CalendarState(
    private val initialTime: Long,
) {
    val prevLastDay = Calendar.getInstance(Locale.KOREA).apply {
        time = Date(initialTime)
        set(Calendar.DATE, 1)
        add(Calendar.DATE, -1)
        set(Calendar.HOUR_OF_DAY, 0)
        clear(Calendar.MINUTE)
        clear(Calendar.SECOND)
        clear(Calendar.MILLISECOND)
    }
    val curLastDay = Calendar.getInstance(Locale.KOREA).apply {
        time = Date(initialTime)
        add(Calendar.MONTH, 1)
        set(Calendar.DATE, 1)
        add(Calendar.DATE, -1)
        set(Calendar.HOUR_OF_DAY, 0)
        clear(Calendar.MINUTE)
        clear(Calendar.SECOND)
        clear(Calendar.MILLISECOND)
    }
}

@Composable
fun rememberCalendarState(
    currentMonth: Long
): State<CalendarState> {
    return CalendarState(currentMonth).let {
        remember { mutableStateOf(it) }.apply { value = it }
    }
}