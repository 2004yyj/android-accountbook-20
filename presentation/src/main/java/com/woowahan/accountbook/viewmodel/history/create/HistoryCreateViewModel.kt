package com.woowahan.accountbook.viewmodel.history.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.ui.theme.Blue1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryCreateViewModel: ViewModel() {

    private val _paymentMethods = MutableStateFlow<List<PaymentMethod>>(emptyList())
    val paymentMethods = _paymentMethods.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    fun getPaymentMethods() {
        viewModelScope.launch {
            _paymentMethods.emit(
                listOf(
                    PaymentMethod("토스뱅크카드"),
                    PaymentMethod("현대카드"),
                    PaymentMethod("BC카드"),
                )
            )
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.emit(
                listOf(
                    Category("expense", "교통비", Blue1.value.toLong()),
                    Category("income", "용돈", Blue1.value.toLong()),
                    Category("expense", "밥값", Blue1.value.toLong()),
                    Category("income", "월급", Blue1.value.toLong()),
                    Category("expense", "교통비", Blue1.value.toLong()),
                )
            )
        }
    }
}
