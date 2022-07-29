package com.woowahan.accountbook.components.history

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.woowahan.accountbook.components.appbar.BackAppBar
import com.woowahan.accountbook.components.appbar.MonthAppBar
import com.woowahan.accountbook.components.checkbox.TypeCheckbox
import com.woowahan.accountbook.components.history.list.HistoryListHeader
import com.woowahan.accountbook.components.history.list.HistoryListItem
import com.woowahan.accountbook.navigation.Screen
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.*
import com.woowahan.accountbook.viewmodel.history.HistoryViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val history by viewModel.history.collectAsState()
    val historyChecked = remember { mutableStateListOf<Int>() }
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isFailure by viewModel.isFailure.collectAsState(initial = "")
    val isSuccessDeleteHistory by viewModel.isSuccessDeleteHistory.collectAsState()
    var currentMonthFirstDayLong by remember { mutableStateOf(System.currentTimeMillis().getCurrentMonthFirstDayMillis()) }
    var forwardMonthFirstDayLong by remember { mutableStateOf(System.currentTimeMillis().getForwardMonthMillis()) }

    val incomeTotal by viewModel.incomeTotal.collectAsState()
    val expenseTotal by viewModel.expenseTotal.collectAsState()

    var isCheckedIncome by rememberSaveable { mutableStateOf(true) }
    var isCheckedExpense by rememberSaveable { mutableStateOf(false) }
    var isModifyModeEnabled by rememberSaveable { mutableStateOf(false) }

    if (isFailure.isNotEmpty()) {
        Toast.makeText(context, isFailure, Toast.LENGTH_SHORT).show()
    }

    fun initial(refreshState: Boolean = false) = run {
        isModifyModeEnabled = false
        viewModel.getHistory(
            currentMonthFirstDayLong,
            forwardMonthFirstDayLong,
            when {
                isCheckedIncome && isCheckedExpense -> "all"
                isCheckedIncome && !isCheckedExpense -> "income"
                isCheckedExpense && !isCheckedIncome -> "expense"
                else -> ""
            },
            refreshState
        )

        viewModel.getTotalPay(
            currentMonthFirstDayLong,
            forwardMonthFirstDayLong,
            "income",
            refreshState
        )

        viewModel.getTotalPay(
            currentMonthFirstDayLong,
            forwardMonthFirstDayLong,
            "expense",
            refreshState
        )
    }

    if (isSuccessDeleteHistory) {
        initial()
    }

    initial()

    Scaffold(
        topBar = {
            if (!isModifyModeEnabled) {
                MonthAppBar(
                    title = { Text(text = currentMonthFirstDayLong.toYearMonth()) },
                    modifier = Modifier.fillMaxWidth(),
                    onClickMonthBack = {
                        currentMonthFirstDayLong = currentMonthFirstDayLong.getBackMonthMillis()
                        forwardMonthFirstDayLong = currentMonthFirstDayLong.getForwardMonthMillis()
                    },
                    onClickMonthForward = {
                        currentMonthFirstDayLong = currentMonthFirstDayLong.getForwardMonthMillis()
                        forwardMonthFirstDayLong = currentMonthFirstDayLong.getForwardMonthMillis()
                    }
                )
            } else {
                BackAppBar(
                    title = { Text(text = "${historyChecked.size}개 선택") },
                    modifier = Modifier.fillMaxWidth(),
                    isModifyModeEnabled = isModifyModeEnabled,
                    onClickBack = { isModifyModeEnabled = false },
                    onClickModify = {
                        viewModel.deleteAllHistory(historyChecked)
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.HistoryIndex.HistoryCreate.route)
                }
            ) {
                Icon(
                    painter = painterResource(Icons.Plus.iconId),
                    contentDescription = "Fab",
                    tint = White
                )
            }
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { initial(true) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Box(modifier = if(history.isEmpty()) Modifier.fillParentMaxSize() else Modifier) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                                    .padding(horizontal = 16.dp)
                            ) {
                                TypeCheckbox(
                                    modifier = Modifier.weight(1f),
                                    title = { Text(text = "수입") },
                                    subtitle = {
                                        if (incomeTotal != 0) {
                                            Text(text = incomeTotal.toMoneyString())
                                        }
                                    },
                                    shape = RadioLeftOption,
                                    checked = isCheckedIncome,
                                    onCheckedChange = {
                                        if (!isModifyModeEnabled) {
                                            isCheckedIncome = it
                                        }
                                    }
                                )

                                TypeCheckbox(
                                    modifier = Modifier.weight(1f),
                                    title = { Text(text = "지출") },
                                    subtitle = {
                                        if (expenseTotal != 0) {
                                            Text(text = expenseTotal.toMoneyString())
                                        }
                                    },
                                    shape = RadioRightOption,
                                    checked = isCheckedExpense,
                                    onCheckedChange = {
                                        if (!isModifyModeEnabled) {
                                            isCheckedExpense = it
                                        }
                                    }
                                )
                            }

                            if (history.isEmpty()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "내역이 없습니다."
                                )
                            }
                        }
                    }

                    history.groupBy { it.date }.forEach { (manufacturer, models) ->

                        val incomeTotal = models.sumOf {
                            if (it.amount > 0) it.amount else 0
                        }.toMoneyString()
                        val expenseTotal = models.sumOf {
                            if (it.amount < 0) (it.amount * -1) else 0
                        }.toMoneyString()

                        stickyHeader {
                            HistoryListHeader(
                                date = manufacturer,
                                incomeTotal = incomeTotal,
                                expenseTotal = expenseTotal
                            )
                        }

                        items(models.count()) { index ->
                            val realIndex = history.indexOf(models[index])
                            HistoryListItem(
                                item = models[index],
                                index = index,
                                count = models.count(),
                                isCheckable = isModifyModeEnabled,
                                isChecked = historyChecked.contains(history[realIndex].id),
                                onCheckedChange = {
                                    if (it) historyChecked.add(history[realIndex].id)
                                    else historyChecked.remove(history[realIndex].id)
                                    if (historyChecked.size == 0 && isModifyModeEnabled)
                                        isModifyModeEnabled = false
                                },
                                onCheckableChange = {
                                    historyChecked.clear()
                                    isModifyModeEnabled = it
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHistoryScreen() {
    HistoryScreen(navController = rememberNavController())
}