package com.woowahan.accountbook.ui.viewmodel.statistics.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.model.Total
import com.woowahan.accountbook.domain.usecase.history.GetAllExpendHistoriesByMonthAndCategoryNameUseCase
import com.woowahan.accountbook.domain.usecase.history.GetTotalListByCategoryNameGroupByDateUseCase
import com.woowahan.accountbook.ui.components.graph.entry.line.LineEntry
import com.woowahan.accountbook.util.toMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsDetailViewModel @Inject constructor(
    private val getAllExpendHistoriesByMonthAndCategoryNameUseCase: GetAllExpendHistoriesByMonthAndCategoryNameUseCase,
    private val getTotalListByCategoryNameGroupByDateUseCase: GetTotalListByCategoryNameGroupByDateUseCase
): ViewModel() {

    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history = _history.asStateFlow()

    private val _totalEntries = MutableStateFlow<List<LineEntry>>(emptyList())
    val totalEntries = _totalEntries.asStateFlow()

    fun getAllHistories(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long,
        name: String
    ) {
        viewModelScope.launch {
            getAllExpendHistoriesByMonthAndCategoryNameUseCase(
                firstDayOfMonth,
                firstDayOfNextMonth,
                name
            ).collect {
                when (it) {
                    is Result.Success<List<History>> -> {
                        _history.emit(it.value)
                    }
                }
            }
        }
    }

    fun getTotalList(name: String) {
        viewModelScope.launch {
            getTotalListByCategoryNameGroupByDateUseCase(name).collect {
                when(it) {
                    is Result.Success<List<Total>> -> {
                        _totalEntries.emit(it.value.map {
                            LineEntry(it.total * -1, it.date.toMonth())
                        })
                    }
                }
            }
        }
    }
}
