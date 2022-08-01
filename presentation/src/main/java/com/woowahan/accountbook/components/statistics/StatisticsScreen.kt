package com.woowahan.accountbook.components.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowahan.accountbook.components.appbar.MonthAppBar
import com.woowahan.accountbook.components.graph.DonutGraph
import com.woowahan.accountbook.components.graph.entry.color.DonutEntry
import com.woowahan.accountbook.util.getBackMonthMillis
import com.woowahan.accountbook.util.getCurrentMonthFirstDayMillis
import com.woowahan.accountbook.util.getForwardMonthMillis
import com.woowahan.accountbook.util.toYearMonth
import com.woowahan.accountbook.viewmodel.StatisticsViewModel

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {

    val entries by viewModel.entries.collectAsState()
    var currentMonth by remember { mutableStateOf(System.currentTimeMillis().getCurrentMonthFirstDayMillis()) }

    viewModel.getAllStatistics(currentMonth, currentMonth.getForwardMonthMillis())
    
    Scaffold(
        topBar = {
            MonthAppBar(
                title = {
                    Text(text = currentMonth.toYearMonth())
                },
                onClickMonthForward = {
                    currentMonth = currentMonth.getForwardMonthMillis()
                },
                onClickMonthBack = {
                    currentMonth = currentMonth.getBackMonthMillis()
                }
            )
        }
    ) {
        Column(Modifier.padding(it)) {
            DonutGraph(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 52.dp, vertical = 24.dp),
                entries = entries
            )
        }
    }
}