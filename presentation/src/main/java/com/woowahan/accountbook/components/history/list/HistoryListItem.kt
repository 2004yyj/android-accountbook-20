package com.woowahan.accountbook.components.history.list

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
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.toMoneyString

@Composable
fun HistoryListItem(
    item: History,
    index: Int = 0,
    count: Int = 0,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 10.dp)
        ) {
                CategoryChip(
                    text = {
                        Text(
                            text = item.category.name,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = Color(item.category.color),
                    modifier = Modifier.align(Alignment.TopStart)
                )
            item.paymentMethod?.name?.let {
                Text(
                    text = it,
                    modifier = Modifier.align(Alignment.TopEnd),
                    color = PurpleLight
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = item.content,
                color = Purple,
                style = Typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )

            Text(
                text = "${item.amount.toMoneyString()}원",
                color = if (item.amount > 0) Success else Error,
                style = Typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }

        if (index == count - 1) {
            Divider(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp),
                thickness = 1.dp,
                color = PurpleLight,
            )
        } else {
            Divider(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = PurpleLight40,
            )
        }
    }
}