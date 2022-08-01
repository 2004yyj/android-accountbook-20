package com.woowahan.accountbook.viewmodel.history.create

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.GetCategoryByNameUseCase
import com.woowahan.accountbook.domain.usecase.category.GetAllCategoryByTypeUseCase
import com.woowahan.accountbook.domain.usecase.category.InsertCategoryUseCase
import com.woowahan.accountbook.domain.usecase.history.InsertHistoryUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.GetAllPaymentMethodsUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.InsertPaymentMethodUseCase
import com.woowahan.accountbook.ui.theme.Blue1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class HistoryCreateViewModel @Inject constructor(
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val getCategoryByNameUseCase: GetCategoryByNameUseCase,
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
    private val getAllPaymentMethodsUseCase: GetAllPaymentMethodsUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val insertPaymentMethodUseCase: InsertPaymentMethodUseCase
): ViewModel() {

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess.asStateFlow()

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

    private val _paymentMethods = MutableStateFlow<List<PaymentMethod>>(emptyList())
    val paymentMethods = _paymentMethods.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    fun insertCategory(type: String, name: String) {
        viewModelScope.launch {
            val randomColor = Color(
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )
            insertCategoryUseCase(type, name, randomColor.value).collect {
                when(it) {
                    is Result.Success<Unit> -> getCategories(type)
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }

    fun insertPaymentMethod(name: String) {
        viewModelScope.launch {
            insertPaymentMethodUseCase(name).collect {
                when(it) {
                    is Result.Success<Unit> -> getPaymentMethods()
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }

    fun getPaymentMethods() {
        viewModelScope.launch {
            getAllPaymentMethodsUseCase().collect {
                when(it) {
                    is Result.Success<List<PaymentMethod>> ->
                        _paymentMethods.emit(it.value)
                    is Result.Failure ->
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                }
            }
        }
    }

    fun getCategories(type: String) {
        viewModelScope.launch {
            getAllCategoryByTypeUseCase(type).collect {
                when(it) {
                    is Result.Success<List<Category>> ->
                        _categories.emit(it.value)
                    is Result.Failure ->
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                }
            }
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
                            _isSuccess.emit(true)
                        }
                    }
                }
            } else {
                insertHistoryUseCase(date, money, filteredContent, category, paymentMethod).collect {
                    _isSuccess.emit(true)
                }
            }
        }
    }

    private suspend fun result(result: Result<*>) {
        when(result) {
            is Result.Failure -> {
                result.cause.message?.let { _isFailure.emit(it) }
            }
        }
    }
}
