package com.woowahan.accountbook.ui.screens.setting

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.woowahan.accountbook.ui.components.appbar.SimpleAppBar
import com.woowahan.accountbook.ui.screens.setting.header.SettingListHeader
import com.woowahan.accountbook.ui.screens.setting.SettingTabs.PaymentMethodTab
import com.woowahan.accountbook.ui.screens.setting.SettingTabs.CategoryIncomeTab
import com.woowahan.accountbook.ui.screens.setting.SettingTabs.CategoryExpenseTab
import com.woowahan.accountbook.ui.screens.setting.item.AddItemFooter
import com.woowahan.accountbook.ui.screens.setting.item.CategoryListItem
import com.woowahan.accountbook.ui.screens.setting.item.PaymentMethodListItem
import com.woowahan.accountbook.ui.screens.setting.mode.SettingMode
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.ui.navigation.Screen
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.viewmodel.setting.SettingViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val paymentMethods by viewModel.paymentMethods.collectAsState()
    val incomeCategories by viewModel.incomeCategories.collectAsState()
    val expenseCategories by viewModel.expenseCategories.collectAsState()

    viewModel.getAllPaymentMethods()
    viewModel.getAllCategoriesByType(PaymentType.Income)
    viewModel.getAllCategoriesByType(PaymentType.Expense)

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

                val settingMode = SettingMode.Modify

                when (settingTab) {
                    PaymentMethodTab -> {
                        items(paymentMethods) { item ->
                            PaymentMethodListItem(paymentMethod = item) {
                                navController.navigate("${Screen.SettingIndex.PaymentMethodCreate.route}?settingMode=$settingMode&id=${item.id}")
                            }
                        }
                    }
                    CategoryExpenseTab -> {
                        items(expenseCategories) { item ->
                            CategoryListItem(category = item) {
                                if (item.name != "미분류/지출") {
                                    navController.navigate("${Screen.SettingIndex.CategoryCreate.route}?settingMode=$settingMode&paymentType=${PaymentType.Expense}&id=${item.id}")
                                } else {
                                    Toast.makeText(context, "기본값은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    CategoryIncomeTab -> {
                        items(incomeCategories) { item ->
                            CategoryListItem(category = item) {
                                if (item.name != "미분류/수입") {
                                    navController.navigate("${Screen.SettingIndex.CategoryCreate.route}?settingMode=$settingMode&paymentType=${PaymentType.Income}&id=${item.id}")
                                } else {
                                    Toast.makeText(context, "기본값은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                item {
                    AddItemFooter(settingTab = settingTab) {
                        val settingMode = SettingMode.Create
                        when (settingTab) {
                            PaymentMethodTab ->
                                navController.navigate("${Screen.SettingIndex.PaymentMethodCreate.route}?settingMode=$settingMode")
                            CategoryIncomeTab, CategoryExpenseTab -> {
                                val type =
                                    if (settingTab == CategoryIncomeTab)
                                        PaymentType.Income
                                    else
                                        PaymentType.Expense
                                navController.navigate("${Screen.SettingIndex.CategoryCreate.route}?settingMode=$settingMode&paymentType=$type")
                            }
                        }
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