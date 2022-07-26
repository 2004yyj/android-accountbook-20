package com.woowahan.accountbook.navigation

sealed class Screen(val url: String) {
    object History : Screen("history") {
        object HistoryCreate : Screen("create")
    }
    object Calendar : Screen("calendar")
    object Statistics: Screen("statistics")
    object Setting: Screen("settings") {
        object CategoryCreate : Screen("categoryCreate")
        object PaymentMethodCreate : Screen("paymentMethodCreate")
    }
}