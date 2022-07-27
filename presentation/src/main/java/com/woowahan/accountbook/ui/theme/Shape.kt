package com.woowahan.accountbook.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val RadioLeftOption = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
val RadioRightOption = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)

val PopupShape = RoundedCornerShape(14.dp)
val SubmitShape = RoundedCornerShape(16.dp)