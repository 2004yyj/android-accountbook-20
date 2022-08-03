package com.woowahan.accountbook.ui.components.graph

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.ui.components.graph.entry.color.DonutEntry
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.ui.theme.PurpleLight40

@Composable
fun DonutGraph(
    entries: List<DonutEntry<Long>>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 60.dp,
    onFooterClick: (DonutEntry<Long>) -> Unit,
    footerContent: @Composable BoxScope.(index: Int, item: DonutEntry<Long>) -> Unit,
) {
    val stroke = with(LocalDensity.current) { Stroke(strokeWidth.toPx()) }
    val angleOffset = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = entries.toString()) {
        angleOffset.snapTo(0f)
        angleOffset.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        )
    }
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Box(Modifier.fillMaxSize()) {
                Canvas(
                    modifier
                        .size(350.dp)
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp)
                ) {
                    val innerRadius = (size.minDimension - stroke.width) / 2
                    val halfSize = size / 2.0f
                    val topLeft = Offset(
                        halfSize.width - innerRadius,
                        halfSize.height - innerRadius
                    )
                    val size = Size(innerRadius * 2, innerRadius * 2)
                    var startAngle = -90f
                    entries.forEachIndexed { index, entries ->
                        val sweep = entries.percent * angleOffset.value
                        drawArc(
                            color = entries.color,
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            topLeft = topLeft,
                            size = size,
                            useCenter = false,
                            style = stroke
                        )
                        startAngle += sweep
                    }
                }
            }
        }

        itemsIndexed(entries) { index, item ->
            Column(
                Modifier.fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .clickable(onClick = { onFooterClick(item) })
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    footerContent(index, item)
                }

                Divider(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = if (index == entries.size - 1) 0.dp else 16.dp),
                    thickness = 1.dp,
                    color = if (index == entries.size - 1) Purple else PurpleLight40,
                )
            }
        }
    }
}
