package com.nullable.ymgalgame.ui.feature.foryou.repository

import com.nullable.ymgalgame.network.NetworkMonitor
import com.nullable.ymgalgame.network.bodyDeserialization
import com.nullable.ymgalgame.ui.feature.foryou.model.TopicCategory
import com.nullable.ymgalgame.ui.feature.foryou.model.TopicsResponse
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ForYouRepository {

    companion object {
        val impl: ForYouRepository = ForYouRepositoryImpl()
    }

    suspend fun getTopics(page: Int, topicCategory: TopicCategory): Flow<TopicsResponse?>


}

class ForYouRepositoryImpl() : ForYouRepository {
    override suspend fun getTopics(page: Int, topicCategory: TopicCategory): Flow<TopicsResponse?> {

        when (topicCategory) {
            TopicCategory.New -> {

                return NetworkMonitor.ktorClient
                    .get(NetworkMonitor.BASE_URL + "/co/topic/list?type=NEWS&page=" + page)
                    .takeIf {
                        it.status == HttpStatusCode.OK
                    }.let {
                        flowOf(
                            it?.bodyDeserialization<TopicsResponse>()
                        )
                    }
            }

            TopicCategory.Impressions -> {

            }
        }
    }
}