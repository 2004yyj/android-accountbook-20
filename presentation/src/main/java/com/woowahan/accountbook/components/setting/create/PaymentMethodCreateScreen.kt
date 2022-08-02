package com.woowahan.accountbook.components.setting.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.woowahan.accountbook.components.appbar.BackAppBar
import com.woowahan.accountbook.components.setting.mode.SettingMode
import com.woowahan.accountbook.ui.theme.*

@Composable
fun PaymentMethodCreateScreen(
    settingMode: String,
    id: Int,
    navController: NavController,
    viewModel: CreatePaymentMethodViewModel = hiltViewModel()
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

    Scaffold(
        topBar = {
            BackAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = "결제 수단 ${modeTitle}하기") },
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {

        }
    }

}