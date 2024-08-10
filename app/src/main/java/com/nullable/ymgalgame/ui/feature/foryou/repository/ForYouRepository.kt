package com.nullable.ymgalgame.ui.feature.foryou.repository

import arrow.core.raise.Raise
import arrow.core.raise.either
import com.nullable.ymgalgame.network.NetworkError
import com.nullable.ymgalgame.network.NetworkMonitor
import com.nullable.ymgalgame.network.NetworkResult
import com.nullable.ymgalgame.network.ResponseResult
import com.nullable.ymgalgame.network.bodyDeserialization
import com.nullable.ymgalgame.ui.feature.foryou.model.DataCategory
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

    suspend fun getTopics(
        page: Int,
        dataCategory: DataCategory
    ): NetworkResult<ResponseResult<TopicsResponse>>

}

class ForYouRepositoryImpl : ForYouRepository {

    override suspend fun getTopics(
        page: Int,
        dataCategory: DataCategory
    ): NetworkResult<ResponseResult<TopicsResponse>> {

        return when (dataCategory) {
            DataCategory.NEWS -> {
                either {
                    getResponseResult(page, dataCategory)
                }
            }

            DataCategory.COLUMN -> {
                either {
                    getResponseResult(page, dataCategory)
                }
            }
        }
    }


    private suspend fun Raise<NetworkError>.getResponseResult(
        page: Int,
        dataCategory: DataCategory,
        networkMonitor: NetworkMonitor = NetworkMonitor
    ): Flow<ResponseResult<TopicsResponse>> = networkMonitor.ktorClient
        .get("${NetworkMonitor.BASE_URL}/co/topic/list?type=${dataCategory.name}&page=$page")
        .ifEx(
            judgment = {
                status == HttpStatusCode.OK
            }, ifTrue = {
                flowOf(bodyDeserialization<TopicsResponse>())
            },
            ifFalse = {
                raise(NetworkError(status.toString()))
            }
        )
}

inline fun <reified R, reified P : Any> P.ifEx(
    judgment: P.() -> Boolean,
    ifTrue: P.() -> R,
    ifFalse: P.() -> R
): R {
    return if (judgment(this)) {
        ifTrue()
    } else {
        ifFalse()
    }
}

