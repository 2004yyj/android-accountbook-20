package com.woowahan.accountbook.ui.viewmodel.setting.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.GetCategoryByIdUseCase
import com.woowahan.accountbook.domain.usecase.category.InsertCategoryUseCase
import com.woowahan.accountbook.domain.usecase.category.UpdateCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
): ViewModel() {
    private val _category = MutableStateFlow<Category?>(null)
    val category = _category.asStateFlow()

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

    private val _isSuccess = MutableStateFlow("")
    val isSuccess = _isSuccess.asStateFlow()

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            getCategoryByIdUseCase(id).collect {
                when(it) {
                    is Result.Success<Category> -> {
                        _category.emit(it.value)
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }

    fun insertCategory(type: PaymentType, name: String, color: ULong) {
        viewModelScope.launch {
            insertCategoryUseCase(type, name, color).collect {
                when(it) {
                    is Result.Success<Unit> -> {
                        _isSuccess.emit("Success")
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }

    fun updateCategory(id: Int, type: PaymentType, name: String, color: ULong) {
        viewModelScope.launch {
            updateCategoryUseCase(id, type, name, color).collect {
                when(it) {
                    is Result.Success<Unit> -> {
                        _isSuccess.emit("Success")
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }
}