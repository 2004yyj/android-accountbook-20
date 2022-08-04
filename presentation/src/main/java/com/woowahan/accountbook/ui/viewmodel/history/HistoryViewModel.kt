package com.woowahan.accountbook.ui.viewmodel.history

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.history.DeleteAllHistoryUseCase
import com.woowahan.accountbook.domain.usecase.history.GetAllHistoriesByMonthAndTypeUseCase
import com.woowahan.accountbook.domain.usecase.history.GetTotalPayByMonthAndTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTotalPayByMonthAndTypeUseCase: GetTotalPayByMonthAndTypeUseCase,
    private val getAllHistoriesByMonthAndTypeUseCase: GetAllHistoriesByMonthAndTypeUseCase,
    private val deleteAllHistoryUseCase: DeleteAllHistoryUseCase
): ViewModel() {
    private val _historyList = MutableStateFlow<SnapshotStateList<History>>(mutableStateListOf())
    val history = _historyList.asStateFlow()

    private val _incomeTotal = MutableStateFlow(0)
    val incomeTotal = _incomeTotal.asStateFlow()

    private val _expenseTotal = MutableStateFlow(0)
    val expenseTotal = _expenseTotal.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun deleteAllHistory(idList: List<History>) {
        viewModelScope.launch {
            deleteAllHistoryUseCase(idList.map { it.id }).collect {
                when (it) {
                    is Result.Success<Unit> -> {
                        _historyList.value.apply {
                            removeAll(idList)
                            _historyList.emit(this)
                        }
                    }
                }
            }
        }
    }

    fun getHistory(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentType, refreshState: Boolean = false) {
        viewModelScope.launch {
            if (refreshState) {
                _isRefreshing.emit(true)
            }
            getAllHistoriesByMonthAndTypeUseCase(firstDayOfMonth, firstDayOfNextMonth, type).collect {
                when (it) {
                    is Result.Success<List<History>> -> {
                        _historyList.emit(it.value.toMutableStateList())
                        _isRefreshing.emit(false)
                    }
                }
            }
        }
    }

    fun getTotalPay(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentType, refreshState: Boolean = false) {
        viewModelScope.launch {
            if (refreshState) {
                _isRefreshing.emit(true)
            }
            getTotalPayByMonthAndTypeUseCase(
                firstDayOfMonth = firstDayOfMonth,
                firstDayOfNextMonth = firstDayOfNextMonth,
                type = type
            ).collect {
                when (it) {
                    is Result.Success<Long> -> {
                        when(type) {
                            PaymentType.Income -> _incomeTotal.emit(it.value.toInt())
                            PaymentType.Expense -> _expenseTotal.emit(it.value.toInt())
                        }
                        _isRefreshing.emit(false)
                    }
                    is Result.Failure -> {
                        _isRefreshing.emit(false)
                    }
                }
            }
        }
    }
}