package com.woowahan.accountbook.viewmodel.history

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.history.GetAllHistoriesByMonthAndTypeUseCase
import com.woowahan.accountbook.domain.usecase.history.GetTotalPayByMonthAndTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTotalPayByMonthAndTypeUseCase: GetTotalPayByMonthAndTypeUseCase,
    private val getAllHistoriesByMonthAndTypeUseCase: GetAllHistoriesByMonthAndTypeUseCase
): ViewModel() {
    private val _historyList = MutableStateFlow<List<History>>(emptyList())
    val history = _historyList.asStateFlow()

    private val _incomeTotal = MutableStateFlow(0)
    val incomeTotal = _incomeTotal.asStateFlow()

    private val _expenseTotal = MutableStateFlow(0)
    val expenseTotal = _expenseTotal.asStateFlow()

    private val _isFailure = MutableSharedFlow<String>()
    val isFailure = _isFailure.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun getHistory(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String = "income") {
        viewModelScope.launch {
            getAllHistoriesByMonthAndTypeUseCase(firstDayOfMonth, firstDayOfNextMonth, type).collect {
                when (it) {
                    is Result.Success<List<History>> -> {
                        _historyList.emit(it.value)
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }

    fun getTotalPay(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String) {
        viewModelScope.launch {
            getTotalPayByMonthAndTypeUseCase(
                firstDayOfMonth = firstDayOfMonth,
                firstDayOfNextMonth = firstDayOfNextMonth,
                type = type
            ).collect {
                when (it) {
                    is Result.Success<Long> -> {
                        if (type == "income") _incomeTotal.emit(it.value.toInt())
                        else if (type == "expense") _expenseTotal.emit(it.value.toInt())
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }
}