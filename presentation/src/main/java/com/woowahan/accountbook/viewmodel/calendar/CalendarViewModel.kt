package com.woowahan.accountbook.viewmodel.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.history.GetAllHistoriesByMonthAndTypeUseCase
import com.woowahan.accountbook.domain.usecase.history.GetTotalPayByMonthAndTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAllHistoriesByMonthAndTypeUseCase: GetAllHistoriesByMonthAndTypeUseCase,
    private val getTotalPayByMonthAndTypeUseCase: GetTotalPayByMonthAndTypeUseCase
): ViewModel() {
    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history = _history.asStateFlow()

    private val _incomeTotal = MutableStateFlow<Long>(0)
    val incomeTotal = _incomeTotal.asStateFlow()

    private val _expenseTotal = MutableStateFlow<Long>(0)
    val expenseTotal = _expenseTotal.asStateFlow()

    fun getTotal(currentMonth: Long, nextMonth: Long, type: String = "income") {
        viewModelScope.launch {
            getTotalPayByMonthAndTypeUseCase(currentMonth, nextMonth, type).collect {
                when(it) {
                    is Result.Success<Long> -> {
                        if (type == "income")
                            _incomeTotal.emit(it.value)
                        else
                            _expenseTotal.emit(it.value)
                    }
                }
            }
        }
    }

    fun getAllHistoriesByMonth(currentMonth: Long, nextMonth: Long) {
        viewModelScope.launch {
            getAllHistoriesByMonthAndTypeUseCase(currentMonth, nextMonth, "all").collect {
                when(it) {
                    is Result.Success<List<History>> -> {
                        _history.emit(it.value)
                        println(it.value)
                    }
                    is Result.Failure -> {
                        it.cause.printStackTrace()
                    }
                }
            }
        }
    }
}
