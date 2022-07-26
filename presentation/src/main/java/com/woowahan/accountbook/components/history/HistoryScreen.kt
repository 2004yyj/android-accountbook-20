package com.woowahan.accountbook.components.history

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HistoryScreen() {
    val name = object{}.javaClass.enclosingClass.name
    Text("Hello $name")
}