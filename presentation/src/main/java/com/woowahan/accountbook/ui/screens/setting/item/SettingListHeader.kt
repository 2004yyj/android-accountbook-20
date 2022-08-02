package com.woowahan.accountbook.ui.screens.setting.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.theme.OffWhite
import com.woowahan.accountbook.ui.theme.PurpleLight
import com.woowahan.accountbook.ui.theme.PurpleLight40
import com.woowahan.accountbook.ui.theme.Typography

@Composable
fun SettingListHeader(
    title: String
) {
    Column(
        modifier =
            Modifier
                .background(OffWhite)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = Typography.h6,
                color = PurpleLight
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = PurpleLight40,
        )
    }
}