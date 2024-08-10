package com.nullable.ymgalgame.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.nullable.ymgalgame.ui.theme.Corner_12
import com.nullable.ymgalgame.ui.theme.Duration_Short4
import com.nullable.ymgalgame.ui.theme.Sizing_56
import com.nullable.ymgalgame.ui.theme.Spacing_16
import com.nullable.ymgalgame.ui.theme.Spacing_4


@Composable
fun YmCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageURL: String,
    headline: String,
    subhead: String,
) {
    var retryHash by remember { mutableIntStateOf(0) }
    OutlinedCard(
        onClick = onClick,
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageURL)
                .setParameter("retry_hash",retryHash)
                .crossfade(durationMillis = Duration_Short4)
                .build(),
            contentDescription = "Image",
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(Corner_12)),
            contentScale = ContentScale.Crop,
            loading = {
                Box(modifier = Modifier
                    .size(Sizing_56)
                    .shimmer())
            },
            error = {
                IconButton(
                    onClick = {retryHash++}
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }

            }
        )
        Column(
            modifier = Modifier.padding(Spacing_16)
        ) {
            Text(text = headline,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(Spacing_4))
            Text(text = subhead,
                style = MaterialTheme.typography.bodySmall)

        }



    }
}