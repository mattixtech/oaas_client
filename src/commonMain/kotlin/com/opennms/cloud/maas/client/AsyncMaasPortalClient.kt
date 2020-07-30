package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.AuthenticationMethod
import com.opennms.cloud.maas.client.model.*
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonElement
import kotlin.js.JsName
import kotlin.jvm.JvmName

expect fun serializer(): JsonSerializer

@OptIn(UnstableDefault::class)
private val jsonSerdes by lazy { Json(JsonConfiguration.Default) }

expect class AsyncResult<T>

expect fun <T> Deferred<T>.toAsyncResult(): AsyncResult<T>

private const val AUTHORIZATION_HEADER = "authorization"

private const val API_SUFFIX = "api/v1/portal"

private const val ONMS_INSTANCE_ENDPOINT = "onms-instance"

class AsyncMaasPortalClient(
        environment: Environment,
        private val authenticationMethod: AuthenticationMethod,
        private val organization: String
) {

    private val baseUrl = "${environment.baseUrl}/$API_SUFFIX"
    private val authToken by lazy { authenticationMethod.authenticationToken }
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = serializer()
        }
    }
    private val requestAuthProvider: HttpRequestBuilder.() -> Unit = { header(AUTHORIZATION_HEADER, authToken) }

    /**
     * The following properties exist to scope the ReST requests to their respective CRUD operation.
     */
    @JsName("create")
    val create = Create()
        @JvmName("create")
        get

    @JsName("read")
    val read = Read()
        @JvmName("read")
        get


    @JsName("update")
    val update = Update()
        @JvmName("update")
        get

    @JsName("delete")
    val delete = Delete()
        @JvmName("delete")
        get

    private fun <T> doAsync(block: suspend () -> T) = GlobalScope.async { block() }.toAsyncResult()

    inner class Create {

        fun onmsInstance(onmsInstance: OnmsInstanceRequestEntity) = doAsync { createFromJson(urlForOrgEndpoint(ONMS_INSTANCE_ENDPOINT), onmsInstance) }

    }

    inner class Read {

        @JsName("onmsInstances")
        fun onmsInstances() = doAsync { getPaginatedJson<OnmsInstanceResponseEntity>(urlForOrgEndpoint(ONMS_INSTANCE_ENDPOINT)) }

    }

    inner class Update {
    }

    inner class Delete {

    }

    private fun urlForOrgEndpoint(endpoint: String) = "$baseUrl/$organization/$endpoint"

    @OptIn(ImplicitReflectionSerializer::class)
    private suspend inline fun <reified T : Any> getPaginatedJson(url: String): PaginatedResponse<T> =
            client.get<Map<String, JsonElement>>(url, requestAuthProvider).let { jsonMap ->
                val totalRecords = requireNotNull(jsonMap[TOTAL_RECORDS_KEY])
                        .primitive
                        .int
                val pagedRecords = requireNotNull(jsonMap[PAGED_RECORDS_KEY])
                        .jsonArray
                        .map { jsonSerdes.fromJson(it) as T }

                PaginatedResponse(totalRecords = totalRecords, records = pagedRecords)
            }

    private suspend fun <T : RequestEntity> createFromJson(url: String, entity: T): String =
            client.post(url) {
                requestAuthProvider()
                contentType(ContentType.Application.Json)
                body = entity
            }

}
