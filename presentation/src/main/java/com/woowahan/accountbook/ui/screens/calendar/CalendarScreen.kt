package com.woowahan.accountbook.ui.screens.calendar

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
import com.woowahan.accountbook.ui.components.appbar.MonthAppBar
import com.woowahan.accountbook.ui.screens.calendar.list.AccountBookCalendar
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.ui.state.rememberCalendarState
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.*
import com.woowahan.accountbook.ui.viewmodel.calendar.CalendarViewModel
import com.woowahan.accountbook.ui.viewmodel.main.MainViewModel

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
        currentMonth.getForwardMonthMillis(),
        PaymentType.Income
    )

    viewModel.getTotal(
        currentMonth,
        currentMonth.getForwardMonthMillis(),
        PaymentType.Expense
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
                        text = "????????? ????????????."
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
                                text = "??????",
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
                                text = "??????",
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
                                text = "??????",
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
                    Box(
                        modifier = Modifier
                            .height(85.dp)
                            .width(50.dp)
                            .align(Alignment.Center)
                    ) {
                        Column(
                            Modifier
                                .wrapContentSize()
                                .align(Alignment.TopStart)
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
                                            text = expense.toMoneyString(),
                                            fontSize = 10.sp,
                                            color = Error,
                                            maxLines = 1,
                                            fontWeight = FontWeight.Bold
                                        )
                                    if ((income != 0L || expense != 0L) && income + expense != 0L)
                                        Text(
                                            text = (expense + income).toMoneyString(),
                                            fontSize = 10.sp,
                                            color = Purple,
                                            maxLines = 1,
                                            fontWeight = FontWeight.Bold
                                        )
                                }
                            }
                        }
                        Text(
                            text = date.toString(),
                            fontSize = 10.sp,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.BottomEnd),
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrentMonth) Purple else PurpleLight40
                        )
                    }
                }
            }
        }
    }
}