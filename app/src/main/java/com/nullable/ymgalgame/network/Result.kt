package com.nullable.ymgalgame.network

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

typealias NetworkResult<T> = Either<NetworkError, Flow<T>>

typealias ResponseResult<T> = Either<SerializationError, T>

sealed interface Failed {
    val message: String
    val code: Int
    val success: Boolean
}

data class LoginFailed(
    override val message: String,
    override val code: Int,
    override val success: Boolean
) : Failed

sealed interface Error {
    val message: String
}

data class SerializationError(
    override val message: String
) : Error

data class NetworkError(
    override val message: String,
) : Error