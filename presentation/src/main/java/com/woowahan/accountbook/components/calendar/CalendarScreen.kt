package com.woowahan.accountbook.components.calendar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.woowahan.accountbook.ui.theme.Icons

@Composable
fun CalendarScreen() {
    val name = object{}.javaClass.enclosingClass.name
    Text("Hello $name")
}