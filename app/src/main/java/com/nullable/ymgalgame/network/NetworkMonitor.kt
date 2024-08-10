package com.nullable.ymgalgame.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.plugin
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.json.Json

object NetworkMonitor {

    const val BASE_URL = "https://www.ymgal.games"

    val ktorClient = HttpClient(CIO)

    @OptIn(InternalAPI::class)
    val ResponsePlugin = createClientPlugin("Serialization") {
        onResponse { response: HttpResponse ->
            val body = response.content.readUTF8Line()
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

}

@OptIn(ExperimentalSerializationApi::class)
suspend inline fun <reified T> HttpResponse.bodyDeserialization(): T {
    try {
        return Json.decodeFromString<T>(bodyAsText())
    }catch (e: MissingFieldException){
        println("MissingFieldException")
    }
}
