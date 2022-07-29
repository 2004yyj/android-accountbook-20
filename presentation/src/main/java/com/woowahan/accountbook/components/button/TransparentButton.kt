package com.woowahan.accountbook.components.button

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransparentButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
    ) {
        content()
    }
}