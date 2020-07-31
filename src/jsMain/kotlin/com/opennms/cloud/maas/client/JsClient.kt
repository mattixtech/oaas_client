package com.opennms.cloud.maas.client

import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asPromise
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

internal actual fun serializer(): JsonSerializer = KotlinxSerializer(Json(JsonConfiguration(useArrayPolymorphism = true)))

actual typealias AsyncResult<T> = com.opennms.cloud.maas.client.Promise<T>

// This subclass 'Promise' and the 'asDynamic()' below are a workaround for not being able to typealias directly to the
// JS 'Promise' impl: https://youtrack.jetbrains.com/issue/KT-21846
//
// The result is that in JS the returned object looks and behaves as a real JS Promise but in KotlinJS the type is
// dynamic and has to be cast first before it can be treated as a promise.
class Promise<T>(executor: (resolve: (T) -> Unit, reject: (Throwable) -> Unit) -> Unit) : kotlin.js.Promise<T>(executor)

internal actual fun <T> Deferred<T>.toAsyncResult() = asPromise().asDynamic()

internal actual fun coroutineDispatcher() = Dispatchers.Default
