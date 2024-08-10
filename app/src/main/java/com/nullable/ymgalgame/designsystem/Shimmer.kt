package com.nullable.ymgalgame.designsystem

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val Shimmer = "Shimmer"
private const val Duration = 1000

fun Modifier.shimmer(
    loading: Boolean = true,
    paddings: PaddingValues = PaddingValues(),
    initialColor: Color? = null,
    targetColor: Color? = null,
    cornerRadius: Dp = 12.dp,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(
        size = cornerRadius
    ),
    durationMillis: Int = Duration,
) = when {
    loading -> shimmerLoading(
        paddings,
        initialColor,
        targetColor,
        roundedCornerShape,
        durationMillis,
    )
    else -> this
}

private fun Modifier.shimmerLoading(
    paddings: PaddingValues,
    initialColor: Color?,
    targetColor: Color?,
    roundedCornerShape: RoundedCornerShape,
    durationMillis: Int,
) = composed {
    val initial = initialColor ?:  MaterialTheme.colorScheme.primaryContainer
    val target = targetColor ?: MaterialTheme.colorScheme.surfaceContainer

    val transition = rememberInfiniteTransition(label = Shimmer)
    val transitionColor by transition.animateColor(
        initialValue = initial,
        targetValue = target,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = Shimmer,
    )

    padding(paddingValues = paddings)
        .background(
            color = transitionColor,
            shape = roundedCornerShape,
        )
        // Cover content
        .drawWithContent { }
}
