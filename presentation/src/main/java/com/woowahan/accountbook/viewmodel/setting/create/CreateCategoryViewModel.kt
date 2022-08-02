package com.woowahan.accountbook.viewmodel.setting.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.Result
import com.woowahan.accountbook.domain.usecase.category.GetCategoryByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase
): ViewModel() {
    private val _category = MutableStateFlow<Category?>(null)
    val category = _category.asStateFlow()

    private val _isFailure = MutableStateFlow("")
    val isFailure = _isFailure.asStateFlow()

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            getCategoryByIdUseCase(id).collect {
                when(it) {
                    is Result.Success<Category> -> {
                        println(it.value)
                        _category.emit(it.value)
                    }
                    is Result.Failure -> {
                        it.cause.message?.let { message -> _isFailure.emit(message) }
                    }
                }
            }
        }
    }
}