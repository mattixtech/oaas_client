package com.opennms.cloud.maas.client

import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

actual fun serializer(): JsonSerializer = KotlinxSerializer(Json(JsonConfiguration(useArrayPolymorphism = true)))

class JsMaasPortalClient(private val client: CoroutineMaasPortalClient) {

    fun getOnmsInstances() = GlobalScope.promise { client.getOnmsInstances() }

}
