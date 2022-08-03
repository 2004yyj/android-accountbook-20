package com.woowahan.accountbook.ui.screens.statistics.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.woowahan.accountbook.ui.components.appbar.BackAppBar
import com.woowahan.accountbook.ui.components.graph.LineGraph
import com.woowahan.accountbook.ui.theme.White
import com.woowahan.accountbook.ui.viewmodel.statistics.detail.StatisticsDetailViewModel

@Composable
fun StatisticsDetailScreen(
    categoryName: String,
    navController: NavHostController,
    viewModel: StatisticsDetailViewModel = hiltViewModel()
) {
    val entries by viewModel.totalEntries.collectAsState()
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
        }
    }
}