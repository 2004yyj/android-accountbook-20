package com.woowahan.accountbook.components.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.*
import com.woowahan.accountbook.viewmodel.calendar.CalendarViewModel
import com.woowahan.accountbook.viewmodel.main.MainViewModel

@Composable
fun CalendarScreen(
    sharedViewModel: MainViewModel,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val history by viewModel.history.collectAsState()
    val currentMonth by sharedViewModel.currentMonth.collectAsState()
    val calendarState = rememberCalendarState(currentMonth = currentMonth)

    val incomeTotal by viewModel.incomeTotal.collectAsState()
    val expenseTotal by viewModel.expenseTotal.collectAsState()

    viewModel.getAllHistoriesByMonth(
        currentMonth,
        currentMonth.getForwardMonthMillis()
    )

    viewModel.getTotal(
        currentMonth,
        currentMonth.getForwardMonthMillis()
    )

    viewModel.getTotal(
        currentMonth,
        currentMonth.getForwardMonthMillis(),
        "expense"
    )

    Scaffold(
        topBar = {
            MonthAppBar(
                title = { Text(text = currentMonth.toYearMonth()) },
                onClickMonthBack = {
                    sharedViewModel.changeToBackMonth()
                },
                onClickMonthForward = {
                    sharedViewModel.changeToNextMonth()
                }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (incomeTotal == 0L && expenseTotal == 0L) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 9.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "내역이 없습니다."
                    )
                }
            } else {
                AccountBookCalendar(
                    modifier = Modifier.fillMaxSize(),
                    calendarState = calendarState,
                    dividerColor = PurpleLight40,
                    footer = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                        ) {
                            Text(
                                text = "수입",
                                color = Purple,
                                style = Typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )

                            Text(
                                text = incomeTotal.toMoneyString(),
                                color = Success,
                                fontWeight = FontWeight.Bold,
                                style = Typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth(),
                            color = PurpleLight40
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = "지출",
                                color = Purple,
                                style = Typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )

                            Text(
                                text = (expenseTotal * -1).toMoneyString(),
                                color = Error,
                                fontWeight = FontWeight.Bold,
                                style = Typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth(),
                            color = PurpleLight40
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = "총합",
                                color = Purple,
                                style = Typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )

                            Text(
                                text = (incomeTotal - expenseTotal).toMoneyString(),
                                color = Purple,
                                fontWeight = FontWeight.Bold,
                                style = Typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
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
}