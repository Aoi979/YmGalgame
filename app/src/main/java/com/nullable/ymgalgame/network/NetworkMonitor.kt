package com.nullable.ymgalgame.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import arrow.core.Either
import arrow.core.raise.Raise
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.json.Json

object NetworkMonitor {

    const val BASE_URL = "https://www.ymgal.games"

    val ktorClient = HttpClient(CIO)

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

//    fun Raise<LoginFailed>.login(username: String, password: String):

}

@OptIn(ExperimentalSerializationApi::class)
suspend inline fun <reified T> HttpResponse.bodyDeserialization(): Either<SerializationError,T> {
    try {
        return Either.Right(Json.decodeFromString<T>(bodyAsText()))
    }catch (e: MissingFieldException){
        Log.e("debug","MissingFieldException//bodyDeserialization")
        return Either.Left(SerializationError(e.message!!))
    }
}
