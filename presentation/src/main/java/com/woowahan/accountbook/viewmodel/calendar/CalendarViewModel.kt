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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAllHistoriesByMonthAndTypeUseCase: GetAllHistoriesByMonthAndTypeUseCase,
): ViewModel() {
    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history = _history.asStateFlow()

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
