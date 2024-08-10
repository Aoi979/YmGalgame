package com.nullable.ymgalgame.ui.feature.foryou.model

import kotlinx.serialization.Serializable

enum class TopicCategory{
    New,
    Impressions
}

@Serializable
data class TopicsResponse(
    val isSuccess: Boolean = false,
    val code: Int,
    val topics: List<Topic>
)

@Serializable
data class Topic(
    val author: Int,
    val createAt: String,
    val favoritesNum: Int,
    val introduction: String,
    val likesNum: Int,
    val mainImg: String,
    val publishTime: String,
    val publishTimeText: String,
    val replyNum: Int,
    val title: String,
    val topicCategory: TopicCategory,
    val topicId: Long,
    val views: Int
)
