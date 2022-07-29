package com.woowahan.accountbook.components.spinner

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.button.TransparentButton
import com.woowahan.accountbook.ui.theme.Icons
import com.woowahan.accountbook.ui.theme.PopupShape
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight

@Composable
fun CustomDropDownMenu(
    value: String,
    onChangedValue: (String) -> Unit,
    onDismissRequest: () -> Unit,
    items: List<String>,
    footerItem: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    var expended by remember { mutableStateOf(false) }
    val arrowAnim by animateFloatAsState(
        animationSpec = tween(400),
        targetValue = if (expended) 180f else 0f
    )

    Column {
        TransparentButton(
            modifier = modifier.fillMaxWidth(),
            onClick = {
                expended = !expended
            }
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = value.ifEmpty { "입력하세요" },
                fontWeight = FontWeight.Bold,
                color = if (value.isNotEmpty()) Purple else PurpleLight
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .graphicsLayer(rotationZ = arrowAnim),
                painter = painterResource(Icons.ArrowBottom.iconId),
                contentDescription = "Close",
            )
        }

        MaterialTheme(
            shapes = MaterialTheme.shapes
                .copy(medium = PopupShape)
        ) {
            DropdownMenu(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.66f)
                    .border(
                        border = BorderStroke(1.dp, Purple),
                        shape = PopupShape
                    ),
                expanded = expended,
                onDismissRequest = {
                    expended = false
                    onDismissRequest()
                }
            ) {
                items.forEach {
                    DropdownMenuItem(onClick = {
                        onChangedValue(it)
                        expended = false
                    }) {
                        Text(text = it, color = Purple)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MenuDefaults.DropdownMenuItemContentPadding)
                ) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                        LocalContentColor provides Purple,
                    ) {
                        footerItem()
                    }
                }
            }
        }
    }
}