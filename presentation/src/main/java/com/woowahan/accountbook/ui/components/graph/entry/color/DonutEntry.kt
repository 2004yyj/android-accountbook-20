package com.woowahan.accountbook.ui.components.graph.entry.color

import androidx.compose.ui.graphics.Color

data class DonutEntry<T: Number>(
    val percent: Float,
    val label: String,
    val value: T,
    val color: Color
)