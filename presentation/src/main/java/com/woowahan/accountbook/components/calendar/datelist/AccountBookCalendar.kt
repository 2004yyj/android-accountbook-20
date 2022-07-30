package com.woowahan.accountbook.components.calendar.datelist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.woowahan.accountbook.state.CalendarState
import java.util.*
import kotlin.collections.ArrayList

private class CalendarDate(
    val date: Int,
    val isCurrentMonth: Boolean
)

@Composable
fun AccountBookCalendar(
) {
    val calendarDates = ArrayList<CalendarDate>()

    calendarState.run {
        val prevLastDate = prevLastDay.get(Calendar.DATE)
        val prevLastDayOfWeek = prevLastDay.get(Calendar.DAY_OF_WEEK)

        val curLastDate = curLastDay.get(Calendar.DATE)
        val curLastDayOfWeek = curLastDay.get(Calendar.DAY_OF_WEEK)

        if (prevLastDayOfWeek != 6) {
            for (i in prevLastDayOfWeek - 1 downTo 0) {
                calendarDates.add(CalendarDate(prevLastDate-i, false))
            }
        }

        for (i in 1..curLastDate) {
            calendarDates.add(CalendarDate(i, false))
        }

        for (i in 1 until 8 - curLastDayOfWeek) {
            calendarDates.add(CalendarDate(i, false))
        }
    }

}