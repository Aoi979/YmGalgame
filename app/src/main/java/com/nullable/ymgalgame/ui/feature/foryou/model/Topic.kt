package com.nullable.ymgalgame.ui.feature.foryou.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TopicCategory{
    @SerialName("资讯")
    New,
    @SerialName("感想评测")
    Impressions
}

@Serializable
data class TopicsResponse(
    @SerialName("success")
    val isSuccess: Boolean = false,
    val code: Int,
    @SerialName("data")
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
