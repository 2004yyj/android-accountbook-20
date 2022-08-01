package com.woowahan.accountbook.components.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.appbar.MonthAppBar
import com.woowahan.accountbook.components.graph.DonutGraph
import com.woowahan.accountbook.components.graph.entry.color.DonutEntry
import com.woowahan.accountbook.ui.theme.Blue1
import com.woowahan.accountbook.ui.theme.Green5
import com.woowahan.accountbook.ui.theme.Purple1
import com.woowahan.accountbook.ui.theme.Yellow1
import com.woowahan.accountbook.util.getBackMonthMillis
import com.woowahan.accountbook.util.getCurrentMonthFirstDayMillis
import com.woowahan.accountbook.util.getForwardMonthMillis
import com.woowahan.accountbook.util.toYearMonth

@Composable
fun StatisticsScreen() {

    val entries = remember { mutableStateListOf(
        DonutEntry(0.3f, "생활", Blue1),
        DonutEntry(0.3f, "생활", Purple1),
        DonutEntry(0.3f, "생활", Green5),
        DonutEntry(0.1f, "생활", Yellow1)
    ) }
    var currentMonth by remember { mutableStateOf(System.currentTimeMillis().getCurrentMonthFirstDayMillis()) }
    
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