package com.opennms.cloud.maas

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class MaasClient(private val endpoint: String) {
    suspend fun doIt(path:String): String {
        val client = HttpClient()
        val response = client.get<String>("$endpoint/$path")
        
        return response
    }
}