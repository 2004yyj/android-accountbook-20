package com.woowahan.accountbook.components.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowahan.accountbook.components.appbar.MonthAppBar
import com.woowahan.accountbook.components.chip.CategoryChip
import com.woowahan.accountbook.components.graph.DonutGraph
import com.woowahan.accountbook.components.graph.entry.color.DonutEntry
import com.woowahan.accountbook.ui.theme.Error
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight
import com.woowahan.accountbook.ui.theme.PurpleLight40
import com.woowahan.accountbook.util.*
import com.woowahan.accountbook.viewmodel.StatisticsViewModel
import com.woowahan.accountbook.viewmodel.main.MainViewModel
import kotlin.math.nextUp
import kotlin.math.round

@Composable
fun StatisticsScreen(
    sharedViewModel: MainViewModel,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val entries by viewModel.entries.collectAsState()
    val total by viewModel.total.collectAsState()
    val currentMonth by sharedViewModel.currentMonth.collectAsState()

    viewModel.getAllStatistics(currentMonth, currentMonth.getForwardMonthMillis())
    viewModel.getTotalPayExpense(currentMonth, currentMonth.getForwardMonthMillis())
    
    Scaffold(
        topBar = {
            MonthAppBar(
                title = {
                    Text(text = currentMonth.toYearMonth())
                },
                onClickMonthForward = {
                    sharedViewModel.changeToNextMonth()
                },
                onClickMonthBack = {
                    sharedViewModel.changeToBackMonth()
                }
            )
        }
    ) {
        Column(Modifier.padding(it)) {
            if (entries.isEmpty()) {
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
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 9.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = "이번 달 총 지출 금액",
                        color = Purple,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        text = (total * -1).toMoneyString(),
                        color = Error,
                        fontWeight = FontWeight.Bold
                    )
                }

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = PurpleLight
                )

                DonutGraph(
                    modifier = Modifier
                        .size(350.dp),
                    entries = entries
                ) { index, item ->
                    Row(
                        Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxSize()
                    ) {
                        CategoryChip(
                            text = {
                                Text(
                                    text = item.label,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            color = item.color,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = item.value.toMoneyString(),
                            color = Purple
                        )
                    }

                    Text(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        text = "${round(item.percent * 100).toInt()}%",
                        color = Purple,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}