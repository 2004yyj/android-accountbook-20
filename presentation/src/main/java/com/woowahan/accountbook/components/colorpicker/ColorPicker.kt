package com.woowahan.accountbook.components.colorpicker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.woowahan.accountbook.components.button.TransparentButton

@Composable
fun ColorPicker(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    content: @Composable LazyGridItemScope.(Color) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(10),
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        items(colors) { item ->
            content(item)
        }
    }
}

@Composable
fun ColorPickerItem(
    color: Color,
    onClick: () -> Unit,
    isChecked: Boolean = false,
) {
    val animationState by animateFloatAsState(
        targetValue = if (isChecked) 24.dp.value else 20.dp.value,
        animationSpec = tween(300)
    )

    Column(modifier = Modifier.wrapContentSize()) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .size(24.dp)
        ) {
            TransparentButton(
                modifier = Modifier
                    .size(
                        width = animationState.dp,
                        height = animationState.dp
                    )
                    .background(color)
                    .align(Alignment.Center),
                onClick = onClick
            )
        }
    }

}