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
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowahan.accountbook.components.appbar.MonthAppBar
import com.woowahan.accountbook.components.calendar.datelist.AccountBookCalendar
import com.woowahan.accountbook.state.rememberCalendarState
import com.woowahan.accountbook.ui.theme.Error
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight40
import com.woowahan.accountbook.ui.theme.Success
import com.woowahan.accountbook.util.*
import com.woowahan.accountbook.viewmodel.calendar.CalendarViewModel

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val history by viewModel.history.collectAsState()
    var currentMonth by remember { mutableStateOf(System.currentTimeMillis().getCurrentMonthFirstDayMillis()) }
    val calendarState = rememberCalendarState(currentMonth = currentMonth)
    viewModel.getAllHistoriesByMonth(
        currentMonth,
        currentMonth.getForwardMonthMillis()
    )

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
                            val filter = history.filter { it.date == currentMonth.getCurrentDateMillis(date) }
                            if (filter.isNotEmpty()) {
                                var income = 0L
                                var expense = 0L
                                filter.forEach {
                                    if (it.amount > 0) income += it.amount
                                    else expense += it.amount
                                }
                                if (income != 0L)
                                    Text(
                                        text = income.toMoneyString(),
                                        fontSize = 10.sp,
                                        color = Success,
                                        maxLines = 1,
                                        fontWeight = FontWeight.Bold
                                    )
                                if (expense != 0L)
                                    Text(
                                        text = (expense * -1).toMoneyString(),
                                        fontSize = 10.sp,
                                        color = Error,
                                        maxLines = 1,
                                        fontWeight = FontWeight.Bold
                                    )
                            }
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