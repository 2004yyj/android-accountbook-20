package com.woowahan.accountbook.ui.screens.setting.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.woowahan.accountbook.ui.components.appbar.BackAppBar
import com.woowahan.accountbook.ui.components.editable.Editable
import com.woowahan.accountbook.ui.screens.setting.mode.SettingMode
import com.woowahan.accountbook.ui.components.textfield.CustomTextField
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.ui.viewmodel.setting.create.CreatePaymentMethodViewModel

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
            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Editable(
                        text = { Text(text = "이름") }
                    ) {
                        CustomTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            placeholder = { Text(text = "입력하세요", fontWeight = FontWeight.Bold) },
                            singleLine = true,
                            value = enterName,
                            onValueChange = { enterName = it },
                        )
                    }
                }
            }

            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Yellow,
                        disabledBackgroundColor = Yellow80
                    ),
                    enabled = enterName.isNotEmpty(),
                    shape = SubmitShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {
                        if (settingMode == SettingMode.Create) {
                            viewModel.insertPaymentMethod(enterName)
                        } else {
                            viewModel.updatePaymentMethod(id, enterName)
                        }
                    }
                ) {
                    Text(text = "${modeTitle}하기", color = White)
                }
            }

        }
    }

}