package com.woowahan.accountbook.ui.viewmodel.main

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.CreateCategoryTableUseCase
import com.woowahan.accountbook.domain.usecase.category.InsertCategoryUseCase
import com.woowahan.accountbook.domain.usecase.history.CreateHistoryTableUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.CreatePaymentMethodTableUseCase
import com.woowahan.accountbook.local.helper.DatabaseOpenHelper
import com.woowahan.accountbook.util.getBackMonthMillis
import com.woowahan.accountbook.util.getCurrentMonthFirstDayMillis
import com.woowahan.accountbook.util.getForwardMonthMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    dbHelper: DatabaseOpenHelper,
    private val createCategoryTableUseCase: CreateCategoryTableUseCase,
    private val createPaymentMethodTableUseCase: CreatePaymentMethodTableUseCase,
    private val createHistoryTableUseCase: CreateHistoryTableUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase
): ViewModel() {
    init {
        dbHelper.setOnUpgradeListener { oldVersion, newVersion ->
            createTables()
        }
    }

    private val _currentMonth =
        MutableStateFlow(
            System.currentTimeMillis().getCurrentMonthFirstDayMillis()
        )
    val currentMonth = _currentMonth.asStateFlow()

    fun createTables() {
        viewModelScope.launch {
            val categoryResult = createCategoryTableUseCase().last()
            val paymentMethodResult = createPaymentMethodTableUseCase().last()
            val historyResult = createHistoryTableUseCase().last()
            when {
                categoryResult is Result.Success<Unit> &&
                        paymentMethodResult is Result.Success<Unit> &&
                        historyResult is Result.Success<Unit> -> {
                    insertDefaultData()
                }
            }
        }
    }

    private fun insertDefaultData() {
        viewModelScope.launch {
            val incomeColor = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            val expenseColor = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            insertCategoryUseCase.invoke(PaymentType.Income, "미분류/수입", incomeColor.value).last()
            insertCategoryUseCase.invoke(PaymentType.Expense, "미분류/지출", expenseColor.value).last()
        }
    }

    fun changeToBackMonth() {
        viewModelScope.launch {
            _currentMonth.emit(_currentMonth.value.getBackMonthMillis())
        }
    }

    fun changeToNextMonth() {
        viewModelScope.launch {
            _currentMonth.emit(_currentMonth.value.getForwardMonthMillis())
        }
    }
}