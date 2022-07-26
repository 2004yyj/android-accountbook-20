package com.woowahan.accountbook.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.theme.Blue1
import com.woowahan.accountbook.ui.theme.White

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit = {},
    color: Color = Blue1
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(shape = CircleShape, color = color)
            .padding(6.dp, 1.dp)
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.high,
            LocalContentColor provides White,
            content = text,
        )
    }
}