package com.woowahan.accountbook.components.history.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight
import com.woowahan.accountbook.ui.theme.PurpleLight40
import com.woowahan.accountbook.ui.theme.Typography
import com.woowahan.accountbook.util.toMonthDate

@Composable
fun HistoryListHeader(
    date: Long = 0L,
    incomeTotal: String = "",
    expenseTotal: String = ""
) {
    Column(
        modifier =
        Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = date.toMonthDate(),
                style = Typography.h6,
                color = PurpleLight
            )
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Text(
                    text = "수입",
                    fontWeight = FontWeight.Bold,
                    color = PurpleLight
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = incomeTotal,
                    fontWeight = FontWeight.Bold,
                    color = PurpleLight
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "지출",
                    fontWeight = FontWeight.Bold,
                    color = PurpleLight
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = expenseTotal,
                    fontWeight = FontWeight.Bold,
                    color = PurpleLight
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = PurpleLight40,
        )
    }
}