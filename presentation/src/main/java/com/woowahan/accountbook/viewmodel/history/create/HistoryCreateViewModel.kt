package com.woowahan.accountbook.viewmodel.history.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.GetCategoryByNameUseCase
import com.woowahan.accountbook.domain.usecase.history.InsertHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryCreateViewModel @Inject constructor(
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val getCategoryByNameUseCase: GetCategoryByNameUseCase
): ViewModel() {

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess.asStateFlow()

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

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
        paymentMethod: PaymentMethod?,
        category: Category?
    ) {
        viewModelScope.launch {
            val filteredContent = content.ifEmpty { "무제" }
            if (category == null) {
                getCategoryByNameUseCase(if (type == "income") "미분류/수입" else "미분류/지출").collect {
                    if (it is Result.Success<Category>) {
                        insertHistoryUseCase(
                            date,
                            money,
                            filteredContent,
                            it.value,
                            paymentMethod
                        ).collect {
                            result(it)
                        }
                    } else {
                        result(it)
                    }
                }
            } else {
                insertHistoryUseCase(date, money, filteredContent, category, paymentMethod).collect {
                    result(it)
                }
            }
        }
    }

    private suspend fun result(result: Result<*>) {
        when(result) {
            is Result.Success<*> -> {
                _isSuccess.emit(true)
            }
            is Result.Failure -> {
                result.cause.message?.let { _isFailure.emit(it) }
            }
        }
    }
}
