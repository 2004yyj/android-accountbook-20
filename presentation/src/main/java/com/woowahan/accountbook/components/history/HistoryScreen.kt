package com.woowahan.accountbook.components.history

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.woowahan.accountbook.components.appbar.MonthAppBar
import com.woowahan.accountbook.navigation.Screen
import com.woowahan.accountbook.ui.theme.Icons
import com.woowahan.accountbook.ui.theme.White
import com.woowahan.accountbook.util.getBackMonthMillis
import com.woowahan.accountbook.util.getForwardMonthMillis
import com.woowahan.accountbook.util.toYearMonth
import com.woowahan.accountbook.viewmodel.history.HistoryViewModel

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = viewModel()
) {
    val history by viewModel.history.collectAsState()
    var currentMonthLong by remember { mutableStateOf(System.currentTimeMillis()) }

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
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(history.count()) {

            }
        }
    }
}