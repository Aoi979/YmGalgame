package com.nullable.ymgalgame.designsystem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.nullable.ymgalgame.ui.theme.Corner_12
import com.nullable.ymgalgame.ui.theme.Duration_Short4

@Composable
fun Image(
    imageUrl: String,
    modifier: Modifier,
    shimmerSize: Dp = LocalConfiguration.current.screenWidthDp.dp
) {
    var retryHash by remember { mutableIntStateOf(0) }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .setParameter("retry_hash", retryHash)
            .crossfade(durationMillis = Duration_Short4)
            .build(),
        contentDescription = "Image",
        modifier = modifier
            .clip(RoundedCornerShape(Corner_12)),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier
                    .size(shimmerSize)
                    .shimmer()
            )
        },
        error = {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = { retryHash++ }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh"
                )
            }

        }
    )
}