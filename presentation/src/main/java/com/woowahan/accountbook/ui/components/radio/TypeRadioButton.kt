package com.woowahan.accountbook.ui.components.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.components.button.TransparentButton
import com.woowahan.accountbook.ui.theme.*

@Composable
fun TypeRadioButton(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    shape: Shape,
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    TransparentButton(
        modifier = modifier
            .height(32.dp)
            .background(
                color =
                if (checked)
                    Purple
                else
                    PurpleLight,
                shape = shape
            ),
        onClick = { onCheckedChange(!checked) }
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        ) {
            ProvideTextStyle(value = Typography.caption) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    LocalContentColor provides White,
                    content = title,
                )
            }
        }
    }
}