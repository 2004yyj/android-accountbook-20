package com.woowahan.accountbook.ui.components.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.theme.*

@Composable
fun MonthAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    onClickMonthBack: () -> Unit = {},
    onClickMonthForward: () -> Unit = {}
) {
    Column {
        TopAppBar(
            modifier = modifier,
            backgroundColor = backgroundColor,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onClickMonthBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(Icons.ArrowLeft.iconId),
                        contentDescription = "MonthBack",
                        tint = Purple
                    )
                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                ) {
                    ProvideTextStyle(value = Typography.h6) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                            LocalContentColor provides Purple,
                            content = title
                        )
                    }
                }

                IconButton(
                    onClick = onClickMonthForward,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(Icons.ArrowRight.iconId),
                        contentDescription = "MonthForward",
                        tint = Purple
                    )
                }
            }
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Purple
        )
    }
}

@Composable
fun BackAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    onClickBack: () -> Unit = {},
    onClickModify: () -> Unit = {},
    isModifyModeEnabled: Boolean = false,
) {
    Column {
        TopAppBar(
            modifier = modifier,
            backgroundColor = backgroundColor,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onClickBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(Icons.Back.iconId),
                        contentDescription = "Back",
                        tint = Purple
                    )
                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                ) {
                    ProvideTextStyle(value = Typography.h6) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                            LocalContentColor provides Purple,
                            content = title
                        )
                    }
                }

                if (isModifyModeEnabled) {
                    IconButton(
                        onClick = onClickModify,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(Icons.Trash.iconId),
                            contentDescription = "ThrowTrash",
                            tint = Error
                        )
                    }
                }
            }
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Purple
        )
    }
}

@Composable
fun SimpleAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    title: @Composable () -> Unit,
) {
    Column {
        TopAppBar(
            modifier = modifier.fillMaxWidth(),
            backgroundColor = backgroundColor,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    ProvideTextStyle(value = Typography.h6) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                            LocalContentColor provides Purple,
                            content = title
                        )
                    }
                }
            }
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Purple
        )
    }
}