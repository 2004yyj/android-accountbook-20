package com.woowahan.accountbook.ui.screens.setting.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.woowahan.accountbook.ui.components.appbar.BackAppBar
import com.woowahan.accountbook.ui.components.colorpicker.ColorPicker
import com.woowahan.accountbook.ui.components.colorpicker.ColorPickerItem
import com.woowahan.accountbook.ui.components.editable.Editable
import com.woowahan.accountbook.ui.type.SettingMode
import com.woowahan.accountbook.ui.components.textfield.CustomTextField
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.ui.viewmodel.setting.create.CreateCategoryViewModel

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
    var enterName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(if (paymentType == PaymentType.Income) Olive1 else Blue1) }

    val modifyData by viewModel.category.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    LaunchedEffect(key1 = isSuccess) {
        if (isSuccess.isNotEmpty()) {
            navController.popBackStack()
        }
    }

    if (settingMode == SettingMode.Modify) {
        viewModel.getCategoryById(id)
    }

    if (modifyData != null) {
        if (colors.contains(Color(modifyData!!.color)))
            selectedColor = Color(modifyData!!.color)
        enterName = modifyData!!.name
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

                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Editable(
                        text = { Text(text = "색상") },
                    ) {}
                }

                ColorPicker(colors = colors) { item ->
                    ColorPickerItem(
                        color = item,
                        isChecked = selectedColor == item,
                        onClick = {
                            selectedColor = item
                        }
                    )
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
                            viewModel.insertCategory(paymentType, enterName, selectedColor.value)
                        } else {
                            viewModel.updateCategory(id, paymentType, enterName, selectedColor.value)
                        }
                    }
                ) {
                    Text(text = "${modeTitle}하기", color = White)
                }
            }

        }
    }

}