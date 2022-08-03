package com.woowahan.accountbook.ui.components.graph.entry.line

import androidx.compose.ui.graphics.Color
import com.woowahan.accountbook.ui.components.graph.entry.Entry

data class LineEntry(
    override val percent: Float,
    override val label: String,
    val value: Long
): Entry()