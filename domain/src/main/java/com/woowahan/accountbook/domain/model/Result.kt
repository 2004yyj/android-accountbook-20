package com.woowahan.accountbook.domain.model

import java.lang.Exception

sealed class Result<out T> {
    class Success<T>(val value: T): Result<T>()
    class Failure(val cause: Exception): Result<Nothing>()
    object Loading: Result<Nothing>()
}