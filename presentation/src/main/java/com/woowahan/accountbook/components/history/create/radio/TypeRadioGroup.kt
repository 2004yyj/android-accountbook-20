package com.woowahan.accountbook.components.history.create.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.button.TransparentButton
import com.woowahan.accountbook.ui.theme.*

@Composable
fun PaymentTypeRadioGroup(
    modifier: Modifier = Modifier,
    items: List<String> = listOf("", ""),
    selectedItem: String = "",
    onClick: (String) -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TransparentButton(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .fillMaxWidth(),
            onClick = { onClick(items[0]) }
        ) {
            Box(modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(
                    color =
                        if (items[0] == selectedItem)
                            Purple
                        else
                            PurpleLight,
                    shape = RadioLeftOption
                )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = items[0],
                    color = White,
                    style = Typography.caption
                )
            }
        }

        TransparentButton(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .fillMaxWidth(),
            onClick = { onClick(items[1]) }
        ) {
            Box(modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(
                    color =
                    if (items[1] == selectedItem)
                        Purple
                    else
                        PurpleLight,
                    shape = RadioRightOption
                )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = items[1],
                    color = White,
                    style = Typography.caption
                )
            }
        }
    }
}
