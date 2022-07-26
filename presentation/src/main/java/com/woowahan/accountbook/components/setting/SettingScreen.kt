package com.woowahan.accountbook.components.setting

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingScreen() {
    val name = object{}.javaClass.enclosingClass.name
    Text("Hello $name")
}