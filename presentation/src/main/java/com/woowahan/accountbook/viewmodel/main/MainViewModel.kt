package com.woowahan.accountbook.viewmodel.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.CreateCategoryTableUseCase
import com.woowahan.accountbook.domain.usecase.category.InsertCategoryUseCase
import com.woowahan.accountbook.domain.usecase.history.CreateHistoryTableUseCase
import com.woowahan.accountbook.domain.usecase.history.InsertHistoryUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.CreatePaymentMethodTableUseCase
import com.woowahan.accountbook.ui.theme.Blue1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.exp

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createCategoryTableUseCase: CreateCategoryTableUseCase,
    private val createPaymentMethodTableUseCase: CreatePaymentMethodTableUseCase,
    private val createHistoryTableUseCase: CreateHistoryTableUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase
): ViewModel() {

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

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
                categoryResult is Result.Failure -> {
                    categoryResult.cause.message?.let { _isFailure.emit(it) }
                }

                paymentMethodResult is Result.Failure -> {
                    paymentMethodResult.cause.message?.let { _isFailure.emit(it) }
                }

                historyResult is Result.Failure -> {
                    historyResult.cause.message?.let { _isFailure.emit(it) }
                }
            }
        }
    }

    private fun insertDefaultData() {
        viewModelScope.launch {
            println(Blue1.value)
            val incomeResult = insertCategoryUseCase.invoke("income", "미분류/수입", Blue1.value).last()
            val expenseResult = insertCategoryUseCase.invoke("expense", "미분류/지출", Blue1.value).last()
        }
    }
}