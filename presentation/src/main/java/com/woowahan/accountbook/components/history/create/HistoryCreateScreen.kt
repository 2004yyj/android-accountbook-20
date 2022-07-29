package com.woowahan.accountbook.components.history.create

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.woowahan.accountbook.components.appbar.BackAppBar
import com.woowahan.accountbook.components.button.TransparentButton
import com.woowahan.accountbook.components.editable.Editable
import com.woowahan.accountbook.components.history.create.radio.TypeRadioButton
import com.woowahan.accountbook.components.spinner.CustomDropDownMenu
import com.woowahan.accountbook.components.textfield.CustomTextField
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.parseMoneyLong
import com.woowahan.accountbook.util.toLongTime
import com.woowahan.accountbook.util.toMoneyString
import com.woowahan.accountbook.util.toYearMonthDayDots
import com.woowahan.accountbook.viewmodel.history.create.HistoryCreateViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import java.time.Instant
import java.time.temporal.ChronoUnit

@OptIn(FlowPreview::class)
@Composable
fun HistoryCreateScreen(
    navController: NavController,
    viewModel: HistoryCreateViewModel = viewModel()
) {
    var selectedIncome by remember { mutableStateOf(true) }
    var selectedExpense by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Instant.now().truncatedTo(ChronoUnit.DAYS).epochSecond * 1000) }
    var enterMoney by remember { mutableStateOf(0L) }
    var enterContent by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val paymentMethods by viewModel.paymentMethods.collectAsState()
    val categories by viewModel.categories.collectAsState()

    viewModel.getPaymentMethods()
    viewModel.getCategories()

    val context = LocalContext.current
    LocalLifecycleOwner.current.apply {
        lifecycleScope.launchWhenResumed {
            viewModel.isSuccess
                .debounce(300)
                .collect {
                    navController.popBackStack()
                }
        }
    }

    val datePickerDialog = DatePickerDialog(context)
    datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
        selectedDate = "$year.${month + 1}.$dayOfMonth".toLongTime("yyyy.MM.dd")
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
                        checked = selectedIncome,
                        onCheckedChange = {
                            if (it) {
                                selectedIncome = it
                                selectedExpense = !it
                            }
                        }
                    )

                    TypeRadioButton(
                        modifier = Modifier.weight(1f),
                        title = { Text("지출") },
                        shape = RadioRightOption,
                        checked = selectedExpense,
                        onCheckedChange = {
                            if (it) {
                                selectedExpense = it
                                selectedIncome = !it
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

                Editable(
                    text = { Text(text = "결제 수단") }
                ) {
                    CustomDropDownMenu(
                        value = selectedPaymentMethod,
                        onChangedValue = { selectedPaymentMethod = it },
                        items = paymentMethods.map { it.name }
                    )
                }

                Editable(
                    text = { Text(text = "분류") }
                ) {
                    CustomDropDownMenu(
                        value = selectedCategory,
                        onChangedValue = { selectedCategory = it },
                        items = categories.map { it.name }
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
                enabled = enterMoney != 0L && selectedPaymentMethod != "",
                shape = SubmitShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    viewModel.insertHistory(
                        type = if (selectedIncome) "income" else "expense",
                        date = selectedDate,
                        money = enterMoney,
                        content = enterContent,
                        paymentMethod = selectedPaymentMethod,
                        category = selectedCategory
                    )
                }
            ) {
                Text(text = "등록하기", color = White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHistoryCreateScreen() {
    HistoryCreateScreen(rememberNavController())
}