package com.woowahan.accountbook.ui.screens.history.create

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.woowahan.accountbook.ui.components.appbar.BackAppBar
import com.woowahan.accountbook.ui.components.button.TransparentButton
import com.woowahan.accountbook.ui.components.editable.Editable
import com.woowahan.accountbook.ui.components.radio.TypeRadioButton
import com.woowahan.accountbook.ui.components.spinner.CustomDropDownMenu
import com.woowahan.accountbook.ui.components.textfield.CustomTextField
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.*
import com.woowahan.accountbook.ui.viewmodel.history.create.HistoryCreateViewModel

@Composable
fun HistoryCreateScreen(
    settingMode: String,
    id: Int,
    navController: NavController,
    viewModel: HistoryCreateViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val isSuccess by viewModel.isSuccess.collectAsState()
    val isFailure by viewModel.isFailure.collectAsState()

    var addingPaymentMethod by remember { mutableStateOf("") }
    var addingCategory by remember { mutableStateOf("") }

    var selectedType by remember { mutableStateOf(PaymentType.Income) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis().getCurrentDateMidNightMillis()) }
    var enterMoney by remember { mutableStateOf(0L) }
    var enterContent by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    var expendedPaymentMethod by remember { mutableStateOf(false) }
    var expendedCategory by remember { mutableStateOf(false) }

    val paymentMethods by viewModel.paymentMethods.collectAsState()
    val categories by viewModel.categories.collectAsState()

    val isSuccessInsertPaymentMethod by viewModel.isSuccessInsertPaymentMethod.collectAsState()
    val isSuccessInsertCategory by viewModel.isSuccessInsertCategory.collectAsState()

    if (isSuccessInsertPaymentMethod.isNotEmpty())
        selectedPaymentMethod = isSuccessInsertPaymentMethod

    if(isSuccessInsertCategory.isNotEmpty())
        selectedCategory = isSuccessInsertCategory

    viewModel.getPaymentMethods()
    viewModel.getCategories(selectedType)

    if (isSuccess) {
        LaunchedEffect(true) {
            navController.popBackStack()
        }
    }

    val datePickerDialog = DatePickerDialog(context)
    datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
        val today = System.currentTimeMillis().getCurrentDateMidNightMillis()
        val pickedDay = "$year.${month + 1}.$dayOfMonth".toLongTime("yyyy.MM.dd")
        if (today >= pickedDay) {
            selectedDate = pickedDay
        } else {
            Toast.makeText(context, "오늘 이후 날짜는 선택할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            BackAppBar(
                title = { Text("내역 등록") },
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(top = 10.dp, bottom = 30.dp)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    TypeRadioButton(
                        modifier = Modifier.weight(1f),
                        title = { Text("수입") },
                        shape = RadioLeftOption,
                        checked = selectedType == PaymentType.Income,
                        onCheckedChange = {
                            if (it) {
                                selectedType = PaymentType.Income
                                selectedPaymentMethod = ""
                                selectedCategory = ""
                                addingPaymentMethod = ""
                                addingCategory = ""
                            } else {
                                selectedType = PaymentType.Expense
                            }
                        }
                    )

                    TypeRadioButton(
                        modifier = Modifier.weight(1f),
                        title = { Text("지출") },
                        shape = RadioRightOption,
                        checked = selectedType == PaymentType.Expense,
                        onCheckedChange = {
                            if (it) {
                                selectedType = PaymentType.Expense
                                selectedPaymentMethod = ""
                                selectedCategory = ""
                                addingPaymentMethod = ""
                                addingCategory = ""
                            } else {
                                selectedType = PaymentType.Income
                            }
                        }
                    )
                }

                Editable(
                    text = { Text(text = "일자") }
                ) {
                    TransparentButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            datePickerDialog.show()
                        }
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = selectedDate.toYearMonthDayDots(),
                            color = Purple,
                            style = Typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Editable(
                    text = { Text(text = "금액") }
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text(text = "입력하세요", fontWeight = FontWeight.Bold) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = if (enterMoney != 0L) enterMoney.toString() else "",
                        onValueChange = {
                            enterMoney = if (it != "") it.parseMoneyLong() else 0L
                        },
                        visualTransformation = { oldString ->
                            val newString = AnnotatedString(oldString.text.toLongOrNull()?.toMoneyString() ?: "")
                            val offsetMapping = object : OffsetMapping {
                                override fun originalToTransformed(offset: Int): Int {
                                    return newString.text.toLongOrNull()?.toMoneyString()?.length ?: 0
                                }

                                override fun transformedToOriginal(offset: Int): Int {
                                    return oldString.text.toLongOrNull()?.toMoneyString()?.length ?: 0
                                }
                            }
                            TransformedText(newString, offsetMapping)
                        }
                    )
                }

                if (selectedType == PaymentType.Expense) {
                    Editable(
                        text = { Text(text = "결제 수단") }
                    ) {
                        CustomDropDownMenu(
                            value = selectedPaymentMethod,
                            onChangedValue = { selectedPaymentMethod = it },
                            onDismissRequest = {
                                addingPaymentMethod = ""
                            },
                            onExpendedChanged = {
                                expendedPaymentMethod = !expendedPaymentMethod
                            },
                            expended = expendedPaymentMethod,
                            items = paymentMethods.map { it.name },
                            footerItem = {
                                CustomTextField(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .padding(end = 48.dp + 16.dp),
                                    singleLine = true,
                                    placeholder = { Text(text = "추가하기", fontWeight = FontWeight.Bold) },
                                    value = addingPaymentMethod,
                                    onValueChange = {
                                        addingPaymentMethod = it
                                    }
                                )

                                IconButton(
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    onClick = {
                                        viewModel.insertPaymentMethod(addingPaymentMethod)
                                        addingPaymentMethod = ""
                                        expendedPaymentMethod = !expendedPaymentMethod
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(Icons.Plus.iconId),
                                        contentDescription = "plus"
                                    )
                                }
                            },
                        )
                    }
                }

                Editable(
                    text = { Text(text = "분류") }
                ) {
                    CustomDropDownMenu(
                        value = selectedCategory,
                        onChangedValue = { selectedCategory = it },
                        onDismissRequest = {
                            addingCategory = ""
                        },
                        onExpendedChanged = {
                            expendedCategory = !expendedCategory
                        },
                        expended = expendedCategory,
                        items = categories.map { it.name },
                        footerItem = {
                            CustomTextField(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(end = 48.dp + 16.dp),
                                singleLine = true,
                                placeholder = { Text(text = "추가하기", fontWeight = FontWeight.Bold) },
                                value = addingCategory,
                                onValueChange = {
                                    addingCategory = it
                                }
                            )

                            IconButton(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = {
                                    viewModel.insertCategory(selectedType, addingCategory)
                                    addingCategory = ""
                                    expendedCategory = !expendedCategory
                                }
                            ) {
                                Icon(
                                    painter = painterResource(Icons.Plus.iconId),
                                    contentDescription = "plus"
                                )
                            }
                        },
                    )
                }

                Editable(
                    text = { Text(text = "내용") }
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text(text = "입력하세요", fontWeight = FontWeight.Bold) },
                        singleLine = true,
                        value = enterContent,
                        onValueChange = { enterContent = it },
                    )
                }
            }

            Button(
                colors = buttonColors(
                    backgroundColor = Yellow,
                    disabledBackgroundColor = Yellow80
                ),
                enabled = enterMoney != 0L && (selectedType == PaymentType.Income || selectedPaymentMethod != ""),
                shape = SubmitShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    viewModel.insertHistory(
                        type = selectedType,
                        date = selectedDate,
                        money = if (selectedType == PaymentType.Income) enterMoney else -enterMoney,
                        content = enterContent,
                        paymentMethod = paymentMethods.find { it.name == selectedPaymentMethod },
                        category = categories.find { it.name == selectedCategory }
                    )
                }
            ) {
                Text(text = "등록하기", color = White)
            }
        }
    }
}