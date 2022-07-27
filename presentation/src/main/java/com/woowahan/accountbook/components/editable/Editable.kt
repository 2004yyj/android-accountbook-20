package com.woowahan.accountbook.components.editable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight40
import com.woowahan.accountbook.ui.theme.Typography
import com.woowahan.accountbook.ui.theme.White

@Composable
fun Editable(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Column(modifier = modifier.padding(top = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                ProvideTextStyle(value = Typography.subtitle1) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                        LocalContentColor provides Purple,
                        content = text,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically)
            ) {
                content()
            }
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = PurpleLight40,
            thickness = 1.dp
        )
    }
}