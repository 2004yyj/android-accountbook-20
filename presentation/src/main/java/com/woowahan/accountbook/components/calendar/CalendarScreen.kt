package com.woowahan.accountbook.components.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahan.accountbook.components.appbar.MonthAppBar
import com.woowahan.accountbook.components.calendar.datelist.AccountBookCalendar
import com.woowahan.accountbook.state.rememberCalendarState
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight40
import com.woowahan.accountbook.util.getBackMonthMillis
import com.woowahan.accountbook.util.getForwardMonthMillis
import com.woowahan.accountbook.util.toYearMonth

@Composable
fun CalendarScreen() {
    var currentMonth by remember { mutableStateOf(System.currentTimeMillis()) }
    val calendarState = rememberCalendarState(currentMonth = currentMonth)

    Scaffold(
        topBar = {
            MonthAppBar(
                title = { Text(text = currentMonth.toYearMonth()) },
                onClickMonthBack = {
                    currentMonth = currentMonth.getBackMonthMillis()
                },
                onClickMonthForward = {
                    currentMonth = currentMonth.getForwardMonthMillis()
                }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AccountBookCalendar(
                modifier = Modifier.fillMaxSize(),
                calendarState = calendarState,
                dividerColor = PurpleLight40
            ) { date, isCurrentMonth ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .align(Alignment.Start)
                    ) {
                        if (isCurrentMonth) {
                            Text(text = "-1,000", fontSize = 10.sp)
                            Text(text = "1,100", fontSize = 10.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = date.toString(),
                        fontSize = 10.sp,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.End),
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrentMonth) Purple else PurpleLight40
                    )
                }
            }
        }
    }
}