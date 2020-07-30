package com.opennms.cloud.maas.client

import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.concurrent.CompletableFuture

@OptIn(UnstableDefault::class)
actual fun serializer(): JsonSerializer = KotlinxSerializer(Json(JsonConfiguration(useArrayPolymorphism = true)))

actual typealias AsyncResult<T> = CompletableFuture<T>

actual fun <T> Deferred<T>.toAsyncResult() = asCompletableFuture()