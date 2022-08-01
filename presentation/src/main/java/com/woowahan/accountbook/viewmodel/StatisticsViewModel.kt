package com.woowahan.accountbook.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.components.graph.entry.color.DonutEntry
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.model.Statistic
import com.woowahan.accountbook.domain.usecase.history.GetAllStatisticsByCategoryTypeUseCase
import com.woowahan.accountbook.domain.usecase.history.GetTotalPayByMonthAndTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getAllStatisticsByCategoryTypeUseCase: GetAllStatisticsByCategoryTypeUseCase,
    private val getTotalPayByMonthAndTypeUseCase: GetTotalPayByMonthAndTypeUseCase
): ViewModel() {

    private val _entries = MutableStateFlow(emptyList<DonutEntry<Long>>())
    val entries = _entries.asStateFlow()

    private val _total = MutableStateFlow(0L)
    val total = _total.asStateFlow()

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

    fun getTotalPayExpense(firstDayOfMonth: Long, firstDayOfNextMonth: Long) {
        viewModelScope.launch {
            getTotalPayByMonthAndTypeUseCase(firstDayOfMonth, firstDayOfNextMonth, "expense").collect {
                when(it) {
                    is Result.Success<Long> -> {
                        _total.emit(it.value)
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }

    fun getAllStatistics(firstDayOfMonth: Long, firstDayOfNextMonth: Long) {
        viewModelScope.launch {
            getAllStatisticsByCategoryTypeUseCase(firstDayOfMonth, firstDayOfNextMonth).collect {
                when(it) {
                    is Result.Success<List<Statistic>> -> {
                        _entries.emit(
                            it.value.map { statistic ->
                                DonutEntry(
                                    statistic.categoryPercent,
                                    statistic.categoryName,
                                    statistic.categoryTotal,
                                    Color(statistic.categoryColor)
                                )
                            }
                        )
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }
}