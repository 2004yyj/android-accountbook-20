package com.woowahan.accountbook.components.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.woowahan.accountbook.components.appbar.SimpleAppBar

@Composable
fun SettingScreen() {
    Scaffold(
        topBar = {
            SimpleAppBar {
                Text(text = "설정")
            }
        }
    ) {
        Column(Modifier.padding(it)) {

        }
    }
}

enum class SettingTabs(val title: String) {
    PaymentMethodTab("결제수단"),
    CategoryExpenseTab("지출 카테고리"),
    CategoryIncomeTab("수입 카테고리")
}