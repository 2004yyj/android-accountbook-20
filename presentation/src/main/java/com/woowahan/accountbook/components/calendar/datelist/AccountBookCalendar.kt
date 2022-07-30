package com.woowahan.accountbook.components.calendar.datelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.state.CalendarState
import java.util.*
import kotlin.collections.ArrayList

private class CalendarDate(
    val date: Int,
    val isCurrentMonth: Boolean
)

@Composable
fun AccountBookCalendar(
    modifier: Modifier = Modifier,
    calendarState: CalendarState,
    dividerColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    content: @Composable() (BoxScope.(date: Int, isCurrentMonth: Boolean) -> Unit),
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
            calendarDates.add(CalendarDate(i, true))
        }

        for (i in 1 until 8 - curLastDayOfWeek) {
            calendarDates.add(CalendarDate(i, false))
        }
    }

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(7),
        content = {
            itemsIndexed(calendarDates) { index, item ->
                Row(Modifier.height(IntrinsicSize.Min)) {
                    Column(Modifier.weight(1f),horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            content(item.date, item.isCurrentMonth)
                        }
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            color = dividerColor
                        )
                    }
                    if ((index + 1) % 7 != 0) {
                        Column {
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                color = dividerColor
                            )
                        }
                    }
                }
            }
        }
    )
}