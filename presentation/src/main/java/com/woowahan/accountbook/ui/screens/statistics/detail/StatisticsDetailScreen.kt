package com.woowahan.accountbook.ui.screens.statistics.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.woowahan.accountbook.ui.components.graph.LineGraph
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

    Column(modifier = Modifier.fillMaxSize()) {
        LineGraph(entries = entries)
        Text(text = "asdfasf")
    }
}