package com.woowahan.accountbook.components.graph.entry.color

import androidx.compose.ui.graphics.Color
import com.woowahan.accountbook.components.graph.entry.Entry

data class DonutEntry(
    override val percent: Float,
    override val label: String,
    val color: Color
): Entry()