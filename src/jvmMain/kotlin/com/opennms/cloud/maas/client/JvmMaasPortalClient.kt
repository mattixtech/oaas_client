package com.opennms.cloud.maas.client

import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

actual fun serializer(): JsonSerializer = KotlinxSerializer(Json(JsonConfiguration(useArrayPolymorphism = true)))

class JvmMaasPortalClient(private val client: CoroutineMaasPortalClient) {

    fun getOnmsinstances() = GlobalScope.future { client.getOnmsInstances() }

}

