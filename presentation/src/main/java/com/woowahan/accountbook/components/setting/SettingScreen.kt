package com.woowahan.accountbook.components.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowahan.accountbook.components.appbar.SimpleAppBar
import com.woowahan.accountbook.components.setting.header.SettingListHeader
import com.woowahan.accountbook.components.setting.SettingTabs.PaymentMethodTab
import com.woowahan.accountbook.components.setting.SettingTabs.CategoryIncomeTab
import com.woowahan.accountbook.components.setting.SettingTabs.CategoryExpenseTab
import com.woowahan.accountbook.components.setting.item.AddItemFooter
import com.woowahan.accountbook.components.setting.item.CategoryListItem
import com.woowahan.accountbook.components.setting.item.PaymentMethodListItem
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.viewmodel.setting.SettingViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val paymentMethods by viewModel.paymentMethods.collectAsState()
    val incomeCategories by viewModel.incomeCategories.collectAsState()
    val expenseCategories by viewModel.expenseCategories.collectAsState()

    viewModel.getAllPaymentMethods()
    viewModel.getAllCategoriesByType("income")
    viewModel.getAllCategoriesByType("expense")

    Scaffold(
        topBar = {
            SimpleAppBar {
                Text(text = "설정")
            }
        }
    ) {
        val settingTabs = SettingTabs.values()
        LazyColumn(Modifier.padding(it)) {
            settingTabs.forEachIndexed { index, settingTab ->
                stickyHeader {
                    SettingListHeader(settingTab.title)
                }

                when (settingTab) {
                    PaymentMethodTab -> {
                        items(paymentMethods) { item ->
                            PaymentMethodListItem(paymentMethod = item)
                        }
                    }
                    CategoryExpenseTab -> {
                        items(expenseCategories) { item ->
                            CategoryListItem(category = item)
                        }

                    }
                    CategoryIncomeTab -> {
                        items(incomeCategories) { item ->
                            CategoryListItem(category = item)
                        }
                    }
                }

                item {
                    AddItemFooter(settingTab = settingTab) {
                        //* OnClick *//
                    }
                }

                item {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Purple,
                    )
                }
            }
        }
    }
}

enum class SettingTabs(val title: String) {
    PaymentMethodTab("결제수단"),
    CategoryExpenseTab("지출 카테고리"),
    CategoryIncomeTab("수입 카테고리")
}