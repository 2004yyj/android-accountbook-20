package com.woowahan.accountbook.domain.model

import java.lang.Exception

sealed class Result<out T> {
    class Success<T>(value: T): Result<T>()
    class Failure(cause: Exception): Result<Nothing>()
    object Loading: Result<Nothing>()
}