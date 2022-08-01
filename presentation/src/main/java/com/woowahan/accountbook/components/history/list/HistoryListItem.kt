package com.woowahan.accountbook.components.history.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.button.TransparentButton
import com.woowahan.accountbook.components.chip.CategoryChip
import com.woowahan.accountbook.domain.model.History
import com.woowahan.accountbook.ui.theme.*
import com.woowahan.accountbook.util.toMoneyString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryListItem(
    item: History,
    isChecked: Boolean,
    isCheckable: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
    onCheckableChange: (Boolean) -> Unit = {}
) {

    val animationState by animateFloatAsState(
        targetValue = if (isChecked && isCheckable) 100.dp.value else 0f,
        animationSpec = tween(300)
    )

    val fadeAnimationState by animateFloatAsState(
        targetValue = if (isChecked && isCheckable) 1f else 0f,
        animationSpec = tween(300)
    )

    TransparentButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (isCheckable) {
                onCheckedChange(!isChecked)
            }
        },
        onLongClick = {
            if (!isCheckable) {
                onCheckableChange(true)
                onCheckedChange(true)
            }
        }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .graphicsLayer(alpha = fadeAnimationState)
                        .align(Alignment.CenterStart),
                    painter = painterResource(Icons.CheckBoxChecked.iconId),
                    tint = Error,
                    contentDescription = "checked"
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .graphicsLayer(translationX = animationState)
                ) {
                    CategoryChip(
                        text = {
                            Text(
                                text = item.category.name,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        color = Color(item.category.color),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.content,
                        color = Purple,
                        style = Typography.h6,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                    Text(
                        text = item.paymentMethod?.name ?: "",
                        color = PurpleLight,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${item.amount.toMoneyString()}원",
                        color = if (item.amount > 0) Success else Error,
                        style = Typography.h6,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}