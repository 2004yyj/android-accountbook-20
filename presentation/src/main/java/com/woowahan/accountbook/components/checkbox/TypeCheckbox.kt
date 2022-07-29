package com.woowahan.accountbook.components.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.button.TransparentButton
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.toMoneyString

@Composable
fun TypeCheckbox(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    subtitle: @Composable () -> Unit = {},
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
            if (checked)
                Icon(
                    painter = painterResource(id = Icons.CheckBoxCheckedWhite.iconId),
                    contentDescription = "CheckBoxChecked",
                    tint = White
                )
            else
                Icon(
                    painter = painterResource(id = Icons.CheckBoxWhite.iconId),
                    contentDescription = "CheckBox",
                    tint = White
                )

            Spacer(modifier = Modifier.width(4.dp))

            ProvideTextStyle(value = Typography.caption) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    LocalContentColor provides White,
                    content = title,
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            ProvideTextStyle(value = Typography.caption) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    LocalContentColor provides White,
                    content = subtitle,
                )
            }
        }
    }
}