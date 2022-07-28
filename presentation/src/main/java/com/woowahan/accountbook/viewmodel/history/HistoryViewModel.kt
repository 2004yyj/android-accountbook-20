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
        }
    }

    fun refresh() {
        viewModelScope.launch {
        }
    }
}