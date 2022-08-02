package com.woowahan.accountbook.components.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.woowahan.accountbook.components.appbar.SimpleAppBar

@Composable
fun SettingScreen() {
    Scaffold(
        topBar = {
            SimpleAppBar {
                Text(text = "설정")
            }
        }
    ) {
        Column(Modifier.padding(it)) {

        }
    }
}