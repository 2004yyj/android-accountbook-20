package com.woowahan.accountbook.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.domain.model.PaymentMethod
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel: ViewModel() {
    private val _historyList = MutableStateFlow<List<History>>(emptyList())
    val history = _historyList.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun getHistory() {
        viewModelScope.launch {
            delay(1000)
            _historyList.emit(listOf(
                    History(
                        0,
                        1658825376000,
                        -10000,
                        "밥",
                        Category("지출", "미분류", 0xFF4A6CC3),
                        PaymentMethod("현대카드")
                    ),
                    History(
                        0,
                        1658707200000,
                        -10000,
                        "밥",
                        Category("지출", "미분류", 0xFF4A6CC3),
                        PaymentMethod("현대카드")
                    ),
                    History(
                        0,
                        1658707200000,
                        10000,
                        "돈",
                        Category("수입", "미분류", 0xFF4A6CC3),
                        PaymentMethod("현대카드")
                    ),
                    History(
                        0,
                        1658620800000,
                        10000,
                        "돈",
                        Category("수입", "미분류", 0xFF4A6CC3),
                        PaymentMethod("현대카드")
                    ),
                ))
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(1000)
            _historyList.emit(listOf(
                History(
                    0,
                    1658825376000,
                    -10000,
                    "밥",
                    Category("지출", "미분류", 0xFF4A6CC3),
                    PaymentMethod("현대카드")
                ),
                History(
                    0,
                    1658707200000,
                    -10000,
                    "밥",
                    Category("지출", "미분류", 0xFF4A6CC3),
                    PaymentMethod("현대카드")
                ),
                History(
                    0,
                    1658707200000,
                    10000,
                    "돈",
                    Category("수입", "미분류", 0xFF4A6CC3),
                    PaymentMethod("현대카드")
                ),
                History(
                    0,
                    1658620800000,
                    10000,
                    "돈",
                    Category("수입", "미분류", 0xFF4A6CC3),
                    PaymentMethod("현대카드")
                ),
            ))
            _isRefreshing.emit(false)
        }
    }
}