package com.woowahan.accountbook.components.setting.create

import androidx.compose.runtime.Composable
import com.woowahan.accountbook.domain.model.PaymentMethod
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.woowahan.accountbook.components.setting.mode.SettingMode
import com.woowahan.accountbook.ui.theme.*

@Composable
fun PaymentMethodCreateScreen(
    settingMode: String,
    id: Int,
    navController: NavController,
    viewModel: CreatePaymentMethodViewModel
) {
    val settingMode = SettingMode.valueOf(settingMode)
    val modeTitle = if (settingMode == SettingMode.Create) "추가" else "수정"
    val isSuccess by viewModel.isSuccess.collectAsState()

    var enterName by remember { mutableStateOf("") }
    val modifyData by viewModel.paymentMethod.collectAsState()

    LaunchedEffect(key1 = isSuccess) {
        if (isSuccess.isNotEmpty()) {
            navController.popBackStack()
        }
    }

    if (settingMode == SettingMode.Modify) {
        viewModel.getPaymentById(id)
    }

    if (modifyData != null) {
        enterName = modifyData!!.name
    }

}