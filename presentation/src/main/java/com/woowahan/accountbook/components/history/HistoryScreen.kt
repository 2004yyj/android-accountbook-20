package com.woowahan.accountbook.components.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.woowahan.accountbook.components.appbar.MonthAppBar
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
    viewModel: HistoryViewModel = viewModel()
) {
    val history by viewModel.history.collectAsState()
    var currentMonthLong by remember { mutableStateOf(System.currentTimeMillis()) }

    viewModel.getHistory()

    Scaffold(
        topBar = {
            MonthAppBar(
                title = { Text(text = currentMonthLong.toYearMonth()) },
                modifier = Modifier.fillMaxWidth(),
                onClickMonthBack = { currentMonthLong = currentMonthLong.getBackMonthMillis() },
                onClickMonthForward = { currentMonthLong = currentMonthLong.getForwardMonthMillis() }
            )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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
                        HistoryListItem(
                            item = models[index],
                            index = index,
                            count = models.count()
                        )
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