package com.woowahan.accountbook.ui.components.graph

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahan.accountbook.ui.components.graph.entry.line.LineEntry
import com.woowahan.accountbook.ui.theme.Purple
import com.woowahan.accountbook.util.toMoneyString

@Composable
fun LineGraph(
    modifier: Modifier = Modifier,
    entries: List<LineEntry>,
) {
    val spacing = 100f
    Box(
        modifier = modifier
            .height(100.dp)
            .padding(top = 40.dp)
    ){
        if (entries.isNotEmpty()) {
            Canvas(
                modifier = modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                val spacePerHour = (size.width - spacing) / entries.size

                val maxHeight = 500
                val maxOfPoints = entries.maxOfOrNull { it.value } ?: 0

                val valueX = mutableListOf<Float>()
                val valueY = mutableListOf<Float>()

               if (entries.size != 1) {
                   val strokePath = Path().apply {
                        for (i in entries.indices) {
                            val realY = maxHeight - ((entries[i].value / maxOfPoints) * maxHeight)
                            val currentX = spacing + i * spacePerHour
                            val currentY = if (realY > 300) 100f else realY.toFloat()
                            if (i == 0) {
                                moveTo(currentX, currentY)
                            } else {
                                lineTo(currentX, currentY)
                            }
                            valueX.add(currentX)
                            valueY.add(currentY - 30)
                        }
                    }

                    drawPath(
                        path = strokePath,
                        color = Purple,
                        style = Stroke(
                            width = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                } else {
                   val realY = maxHeight - ((entries.first().value / maxOfPoints) * maxHeight)
                   val currentY = if (realY > 300) 100f else realY.toFloat()
                   drawCircle(
                       Purple,
                       radius = 3.dp.toPx(),
                       center = Offset(size.width/2, size.height/2)
                   )
                    valueX.add(size.width / 2)
                    valueY.add(currentY)
                }

                val textPaint = Paint().apply {
                    isAntiAlias = true
                    textSize = 14.sp.toPx()
                    color = Color.parseColor("#524D90")
                }

                entries.forEachIndexed { index, item ->
                    val moneyTextWidth = textPaint.measureText(item.value.toMoneyString())
                    val labelTextWidth = textPaint.measureText(item.label)
                    val canvas = drawContext.canvas.nativeCanvas

                    canvas.drawText(
                        item.value.toMoneyString(),
                        valueX[index] - (moneyTextWidth / 2),
                        valueY[index],
                        textPaint
                    )

                    canvas.drawText(
                        item.label,
                        valueX[index] - (labelTextWidth / 2),
                        size.height,
                        textPaint
                    )
                }
            }
        }
    }
}