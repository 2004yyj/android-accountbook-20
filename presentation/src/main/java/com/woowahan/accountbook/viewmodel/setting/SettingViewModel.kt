package com.woowahan.accountbook.viewmodel.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.GetAllCategoriesUseCase
import com.woowahan.accountbook.domain.usecase.category.GetAllCategoryByTypeUseCase
import com.woowahan.accountbook.domain.usecase.category.InsertCategoryUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.GetAllPaymentMethodsUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.InsertPaymentMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getAllPaymentMethodsUseCase: GetAllPaymentMethodsUseCase,
    private val insertPaymentMethodUseCase: InsertPaymentMethodUseCase,
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase
): ViewModel() {
    private val _paymentMethods = MutableStateFlow(emptyList<PaymentMethod>())
    val paymentMethods = _paymentMethods.asStateFlow()

    private val _incomeCategories = MutableStateFlow(emptyList<Category>())
    val incomeCategories = _incomeCategories.asStateFlow()

    private val _expenseCategories = MutableStateFlow(emptyList<Category>())
    val expenseCategories = _expenseCategories.asStateFlow()

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

    fun getAllPaymentMethods() {
        viewModelScope.launch {
            getAllPaymentMethodsUseCase().collect {
                when(it) {
                    is Result.Success<List<PaymentMethod>> -> {
                        _paymentMethods.emit(it.value)
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }

    fun getAllCategoriesByType(type: PaymentType) {
        viewModelScope.launch {
            getAllCategoryByTypeUseCase(type).collect {
                when(it) {
                    is Result.Success<List<Category>> -> {
                        when(type) {
                            PaymentType.Income -> _incomeCategories.emit(it.value)
                            PaymentType.Expense -> _expenseCategories.emit(it.value)
                        }
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }
}
