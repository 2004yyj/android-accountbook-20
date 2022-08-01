package com.woowahan.accountbook.components.graph

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.graph.entry.color.DonutEntry

@Composable
fun DonutGraph(
    entries: List<DonutEntry>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 60.dp
) {
    val currentState = remember { MutableTransitionState(false).apply { targetState = true } }
    val stroke = with(LocalDensity.current) { Stroke(strokeWidth.toPx()) }
    val transition = updateTransition(currentState, label = "")
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        }, label = ""
    ) { if (!it) 0f else 360f }

    Canvas(modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = -90f
        entries.forEachIndexed { index, entries ->
            val sweep = entries.percent * angleOffset
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