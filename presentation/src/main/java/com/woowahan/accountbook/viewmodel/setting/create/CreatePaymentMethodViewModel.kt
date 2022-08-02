package com.woowahan.accountbook.viewmodel.setting.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.paymentmethod.GetPaymentMethodByIdUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.InsertPaymentMethodUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.UpdatePaymentMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePaymentMethodViewModel @Inject constructor(
    private val getPaymentMethodByIdUseCase: GetPaymentMethodByIdUseCase,
    private val insertPaymentMethodUseCase: InsertPaymentMethodUseCase,
    private val updatePaymentMethodUseCase: UpdatePaymentMethodUseCase
) : ViewModel() {
    private val _isSuccess = MutableStateFlow("")
    val isSuccess = _isSuccess.asStateFlow()

    private val _paymentMethod = MutableStateFlow<PaymentMethod?>(null)
    val paymentMethod = _paymentMethod.asStateFlow()

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

    fun getPaymentById(id: Int) {
        viewModelScope.launch {
            getPaymentMethodByIdUseCase(id).collect {
                when(it) {
                    is Result.Success<PaymentMethod> -> {
                        _paymentMethod.emit(it.value)
                    }
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
                when (it) {
                    is Result.Success<Unit> -> {
                        _isSuccess.emit("Success")
                    }
                }
            }
        }
    }

    fun updatePaymentMethod(id: Int, name: String) {
        viewModelScope.launch {
            updatePaymentMethodUseCase(id, name).collect {
                when (it) {
                    is Result.Success<Unit> -> {
                        _isSuccess.emit("Success")
                    }
                }
            }
        }
    }
}
