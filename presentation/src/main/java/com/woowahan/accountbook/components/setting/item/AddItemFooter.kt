package com.woowahan.accountbook.components.setting.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.setting.SettingTabs
import com.woowahan.accountbook.ui.theme.Icons
import com.woowahan.accountbook.ui.theme.Purple

@Composable
fun AddItemFooter(
    settingTab: SettingTabs,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = "${settingTab.title} 추가하기",
            color = Purple,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Icon(
            painter = painterResource(id = Icons.Plus.iconId),
            contentDescription = "Plus",
            tint = Purple,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}