package com.woowahan.accountbook.ui.screens.statistics.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.woowahan.accountbook.ui.components.appbar.BackAppBar
import com.woowahan.accountbook.ui.components.checkbox.TypeCheckbox
import com.woowahan.accountbook.ui.components.graph.LineGraph
import com.woowahan.accountbook.ui.screens.history.list.HistoryListHeader
import com.woowahan.accountbook.ui.screens.history.list.HistoryListItem
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.ui.viewmodel.main.MainViewModel
import com.woowahan.accountbook.ui.viewmodel.statistics.detail.StatisticsDetailViewModel
import com.woowahan.accountbook.util.getForwardMonthMillis
import com.woowahan.accountbook.util.toMoneyString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsDetailScreen(
    categoryName: String,
    navController: NavHostController,
    sharedViewModel: MainViewModel,
    viewModel: StatisticsDetailViewModel = hiltViewModel()
) {
    val entries by viewModel.totalEntries.collectAsState()
    val history by viewModel.history.collectAsState()
    val currentMonth by sharedViewModel.currentMonth.collectAsState()

    viewModel.getAllHistories(
        currentMonth,
        currentMonth.getForwardMonthMillis(),
        categoryName
    )
    LaunchedEffect(key1 = entries.isEmpty()) {
        viewModel.getTotalList(categoryName)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BackAppBar(
                title = { Text(text = categoryName) },
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LineGraph(
                modifier = Modifier
                    .background(White)
                    .padding(bottom = 5.dp),
                entries = entries
            )
            Divider(color = PurpleLight, modifier = Modifier.height(1.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                history.forEachIndexed { index, item ->
                    if (index == 0 || history[index - 1].date != item.date) {
                        stickyHeader {
                            HistoryListHeader(
                                date = item.date,
                                incomeTotal = item.incomeTotalByDate,
                                expenseTotal = item.expenseTotalByDate,
                            )
                        }
                    }

                    item {
                        Column {
                            HistoryListItem(
                                item = item,
                                isChecked = false,
                                isCheckable = false
                            )

                            if (index != history.size - 1 && history[index + 1].date != item.date) {
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp),
                                    thickness = 1.dp,
                                    color = PurpleLight,
                                )
                            } else {
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp)
                                        .padding(horizontal = 16.dp),
                                    thickness = 1.dp,
                                    color = PurpleLight40,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}