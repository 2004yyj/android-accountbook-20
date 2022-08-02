package com.woowahan.accountbook.components.setting.create

import androidx.lifecycle.ViewModel
import com.woowahan.accountbook.domain.model.PaymentMethod
import com.woowahan.accountbook.domain.usecase.paymentmethod.InsertPaymentMethodUseCase
import com.woowahan.accountbook.domain.usecase.paymentmethod.UpdatePaymentMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreatePaymentMethodViewModel @Inject constructor(
    private val insertPaymentMethodUseCase: InsertPaymentMethodUseCase,
    private val updatePaymentMethodUseCase: UpdatePaymentMethodUseCase
) : ViewModel() {
    private val _isSuccess = MutableStateFlow("")
    val isSuccess = _isSuccess.asStateFlow()

    private val _paymentMethod = MutableStateFlow<PaymentMethod?>(null)
    val paymentMethod = _paymentMethod.asStateFlow()

    fun getPaymentById(id: Int) {

    }
}
