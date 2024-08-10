package com.nullable.ymgalgame.ui.feature.foryou

import com.nullable.ymgalgame.ui.feature.foryou.model.Topic


data class ForYouState(
    val isLoading: Boolean = false,
    val newsTopics: List<Topic> = emptyList(),
    val impressionsTopics: List<Topic> = emptyList(),
    val message: String = "success",
)