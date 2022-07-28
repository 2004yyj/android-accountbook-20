package com.woowahan.accountbook.viewmodel.history.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.ui.theme.Blue1
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryCreateViewModel: ViewModel() {

    private val _isSuccess = MutableSharedFlow<Boolean>()
    val isSuccess = _isSuccess.asSharedFlow()

    private val _paymentMethods = MutableStateFlow<List<PaymentMethod>>(emptyList())
    val paymentMethods = _paymentMethods.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    fun getPaymentMethods() {
        viewModelScope.launch {

        }
    }

    fun getCategories() {
        viewModelScope.launch {

        }
    }

    fun insertHistory(
        type: String,
        date: Long,
        money: Long,
        content: String,
        paymentMethod: String,
        category: String
    ) {
        val filteredCategory = category.ifEmpty { "미분류" }
        val filteredContent = content.ifEmpty { "무제" }
    }
}
