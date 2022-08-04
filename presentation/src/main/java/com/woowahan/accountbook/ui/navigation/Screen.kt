package com.woowahan.accountbook.ui.navigation

import androidx.annotation.DrawableRes
import com.woowahan.accountbook.R

sealed class Screen(val route: String) {
    object HistoryIndex: Screen("/history/index") {
        object HistoryCreate: Screen("/create")
    }
    object CalendarIndex: Screen("/calendar/index")
    object StatisticsIndex: Screen("/statistics/index") {
        object Detail: Screen("/statistics/detail")
    }
    object SettingIndex: Screen("/setting/index") {
        object PaymentMethodCreate: Screen("/create/paymentMethod")
        object CategoryCreate: Screen("/create/category")
    }
}

sealed class BottomNavigationRoute(val route: String, val label: String, @DrawableRes val iconId: Int) {
    object History: BottomNavigationRoute("/history", "내역", R.drawable.ic_document_list)
    object Calendar: BottomNavigationRoute("/calendar", "달력", R.drawable.ic_calendar_month)
    object Statistics: BottomNavigationRoute("/statistics", "통계", R.drawable.ic_graph)
    object Setting: BottomNavigationRoute("/setting", "설정", R.drawable.ic_settings)
}