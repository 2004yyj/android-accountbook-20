package com.woowahan.accountbook.components.setting.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.woowahan.accountbook.components.appbar.BackAppBar
import com.woowahan.accountbook.components.colorpicker.ColorPicker
import com.woowahan.accountbook.components.colorpicker.ColorPickerItem
import com.woowahan.accountbook.components.setting.mode.SettingMode
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.viewmodel.setting.create.CreateCategoryViewModel

private val ExpenseColors = listOf(
    Blue1, Blue2, Blue3, Blue4, Blue5,
    Green1, Green2, Green3, Green4, Green5,
    Purple1, Purple2, Purple3, Purple4, Purple5,
    Pink1, Pink2, Pink3, Pink4, Pink5
)

private val IncomeColors = listOf(
    Olive1, Olive2, Olive3, Olive4, Olive5,
    Yellow1, Yellow2, Yellow3, Yellow4, Yellow5
)


@Composable
fun CategoryCreateScreen(
    settingMode: String,
    paymentType: String,
    id: Int,
    navController: NavController,
    viewModel: CreateCategoryViewModel = hiltViewModel()
) {
    val settingMode = SettingMode.valueOf(settingMode)
    val paymentType = PaymentType.valueOf(paymentType)
    val typeTitle = if (paymentType == PaymentType.Income) "수입" else "지출"
    val modeTitle = if (settingMode == SettingMode.Create) "추가" else "수정"

    val colors = if (paymentType == PaymentType.Income) IncomeColors else ExpenseColors
    var selectedItem by remember { mutableStateOf(if (paymentType == PaymentType.Income) Olive1 else Blue1) }

    val modifyData by viewModel.category.collectAsState()

    if (settingMode == SettingMode.Modify) {
        viewModel.getCategoryById(id)
    }

    Scaffold(
        topBar = {
            BackAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = "$typeTitle 카테고리 $modeTitle") },
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            ColorPicker(colors = colors) { item ->
                ColorPickerItem(
                    color = item,
                    isChecked = selectedItem == item,
                    onClick = {
                        selectedItem = item
                    }
                )
            }
        }
    }

}