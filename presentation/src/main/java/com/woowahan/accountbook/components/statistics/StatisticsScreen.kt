package com.woowahan.accountbook.components.statistics

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun StatisticsScreen() {
    val name = object{}.javaClass.enclosingClass.name
    Text("Hello $name")
}