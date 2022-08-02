package com.woowahan.accountbook.ui.screens.history.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.toMoneyString
import com.woowahan.accountbook.util.toMonthDate

@Composable
fun HistoryListHeader(
    date: Long = 0L,
    incomeTotal: Long = 0,
    expenseTotal: Long = 0
) {
    Column(
        modifier =
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(OffWhite)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = date.toMonthDate(),
                style = Typography.h6,
                color = PurpleLight
            )
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                if (incomeTotal != 0L) {
                    Text(
                        text = "수입",
                        fontWeight = FontWeight.Bold,
                        color = PurpleLight
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = incomeTotal.toMoneyString(),
                        fontWeight = FontWeight.Bold,
                        color = PurpleLight
                    )
                }
                if (incomeTotal != 0L && expenseTotal != 0L) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
                if (expenseTotal != 0L) {
                    Text(
                        text = "지출",
                        fontWeight = FontWeight.Bold,
                        color = PurpleLight
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = expenseTotal.toMoneyString(),
                        fontWeight = FontWeight.Bold,
                        color = PurpleLight
                    )
                }
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