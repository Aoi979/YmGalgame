package com.nullable.ymgalgame.network

import com.nullable.ymgalgame.ui.feature.foryou.model.Topic
import kotlinx.coroutines.flow.Flow

sealed class Result {
    data class TopicsResponse(
        val isSuccess: Boolean = false,
        val data: Flow<List<Topic>>
    ): Result()

}