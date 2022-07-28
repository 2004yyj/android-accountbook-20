package com.woowahan.accountbook.viewmodel.main

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.CreateCategoryTableUseCase
import com.woowahan.accountbook.domain.usecase.history.CreateHistoryTableUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.CreatePaymentMethodTableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createCategoryTableUseCase: CreateCategoryTableUseCase,
    private val createPaymentMethodTableUseCase: CreatePaymentMethodTableUseCase,
    private val createHistoryTableUseCase: CreateHistoryTableUseCase
): ViewModel() {

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

    fun createTables() {
        viewModelScope.launch {
            val categoryResult = createCategoryTableUseCase().last()
            val paymentMethodResult = createPaymentMethodTableUseCase().last()
            val historyResult = createHistoryTableUseCase().last()
            when {
                categoryResult is Result.Failure -> {
                    categoryResult.cause.message?.let { _isFailure.emit(it) }
                }

                paymentMethodResult is Result.Failure -> {
                    paymentMethodResult.cause.message?.let { _isFailure.emit(it) }
                }

                historyResult is Result.Failure -> {
                    historyResult.cause.message?.let { _isFailure.emit(it) }
                }
            }
        }
    }
}