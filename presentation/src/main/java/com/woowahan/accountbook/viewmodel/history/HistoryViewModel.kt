package com.woowahan.accountbook.viewmodel.history

import androidx.lifecycle.ViewModel
import com.woowahan.accountbook.domain.model.History
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryViewModel: ViewModel() {
    private val _historyList = MutableStateFlow<List<History>>(emptyList())
    val history = _historyList.asStateFlow()
}