package com.woowahan.accountbook.ui.components.graph.entry.color

import androidx.compose.ui.graphics.Color
import com.woowahan.accountbook.ui.components.graph.entry.Entry

data class DonutEntry<T: Number>(
    override val percent: Float,
    override val label: String,
    val value: T,
    val color: Color
): Entry()