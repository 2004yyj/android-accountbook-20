package com.woowahan.accountbook.components.history.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.woowahan.accountbook.components.appbar.BackAppBar
import com.woowahan.accountbook.components.history.create.radio.PaymentTypeRadioGroup

@Composable
fun HistoryCreateScreen(navController: NavController) {

    var selectedType by remember { mutableStateOf("수입") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackAppBar(
            title = { Text("내역 등록") },
            onClickBack = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .padding(horizontal = 16.dp)
        ) {
            PaymentTypeRadioGroup(
                items = listOf("수입", "지출"),
                selectedItem = selectedType,
                onClick = {
                    selectedType = it
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHistoryCreateScreen() {
    HistoryCreateScreen(rememberNavController())
}