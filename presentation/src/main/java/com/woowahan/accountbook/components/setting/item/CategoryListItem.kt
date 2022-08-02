package com.woowahan.accountbook.components.setting.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.chip.CategoryChip
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight40

@Composable
fun CategoryListItem(
    category: Category,
    onClick: () -> Unit = {}
) {
    Column(
        Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(Modifier.fillMaxSize()) {
            Text(
                text = category.name,
                color = Purple,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            CategoryChip(
                text = {
                    Text(
                        text = category.name,
                        fontWeight = FontWeight.Bold
                    )
                },
                color = Color(category.color),
                modifier = Modifier.align(Alignment.CenterEnd)
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