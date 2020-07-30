package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.AuthenticationMethod
import com.opennms.cloud.maas.client.model.*
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonElement
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

/**
 * Allow platform specific serialization engines to be provided.
 */
internal expect fun serializer(): JsonSerializer

@OptIn(UnstableDefault::class)
private val jsonSerdes by lazy { Json(JsonConfiguration.Default) }

expect class AsyncResult<T>

/**
 * Wrap the result of an asynchronous coroutine execution in a platform specific result type.
 */
internal expect fun <T> Deferred<T>.toAsyncResult(): AsyncResult<T>

/**
 * Delegate to a platform specific coroutine dispatcher so we can take advantage of the IO thread pool on JVM.
 */
internal expect fun coroutineDispatcher(): CoroutineDispatcher

// HTTP consts
private const val AUTHORIZATION_HEADER = "authorization"

// URL consts
private const val API_SUFFIX = "api/v1/portal"

// Endpoint consts
private const val ONMS_INSTANCE_ENDPOINT = "onms-instance"

// Search/Sort/Filter/Paginate consts
private const val ORDER_BY_KEY = "orderBy"
private const val LIMIT_KEY = "limit"
private const val OFFSET_KEY = "offset"
private const val SORT_KEY = "sort"
private const val SEARCH_KEY = "search"
private const val SEARCH_COLUMN_KEY = "searchColumn"

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
    private val coroutineScope = CoroutineScope(coroutineDispatcher())

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

    inner class Create {

        @JsName("onmsInstance")
        fun onmsInstance(onmsInstance: OnmsInstanceRequestEntity) = doAsync { createFromEntity(urlForOrgEndpoint(ONMS_INSTANCE_ENDPOINT), onmsInstance) }

    }

    inner class Read {

        @JsName("onmsInstances")
        @JvmOverloads
        fun onmsInstances(options: ListQueryOptions? = null) = doAsync { getPaginated<OnmsInstanceEntity>(urlForOrgEndpoint(ONMS_INSTANCE_ENDPOINT), options) }

        @JsName("onmsInstance")
        fun onmsInstance(id: String) = doAsync { get<OnmsInstanceEntity>(urlForOrgEndpoint(ONMS_INSTANCE_ENDPOINT), id) }

    }

    inner class Update {

        @JsName("onmsInstance")
        fun onmsInstance(id: String, entity: OnmsInstanceEntity) = doAsync { updateById(urlForOrgEndpoint(ONMS_INSTANCE_ENDPOINT), id, entity) }

    }

    inner class Delete {

        @JsName("onmsInstance")
        fun onmsInstance(id: String) = doAsync { deleteById(urlForOrgEndpoint(ONMS_INSTANCE_ENDPOINT), id) }

    }

    /**
     * Execute the request in a coroutine and wrap it in a platform specific result (Promise on JS/Future on JVM).
     */
    private fun <T> doAsync(block: suspend () -> T) = coroutineScope.async { block() }.toAsyncResult()

    private fun urlForOrgEndpoint(endpoint: String) = "$baseUrl/$organization/$endpoint"

    @OptIn(ImplicitReflectionSerializer::class)
    private suspend inline fun <reified T : Entity> getPaginated(url: String, options: ListQueryOptions?): PaginatedResponse<T> =
            client.get<Map<String, JsonElement>>(url) {
                requestAuthProvider()
                if (options != null) {
                    if (options.orderByColumn != null) parameter(ORDER_BY_KEY, options.orderByColumn)
                    if (options.limit != null) parameter(LIMIT_KEY, options.limit)
                    if (options.offset != null) parameter(OFFSET_KEY, options.offset)
                    if (options.sortOrder != null) parameter(SORT_KEY, options.sortOrder)
                    if (options.searchPrefix != null) parameter(SEARCH_KEY, options.searchPrefix)
                    if (options.searchField != null) parameter(SEARCH_COLUMN_KEY, options.searchField)
                }
            }
                    .let { jsonMap ->
                        val totalRecords = requireNotNull(jsonMap[TOTAL_RECORDS_KEY])
                                .primitive
                                .int
                        val pagedRecords = requireNotNull(jsonMap[PAGED_RECORDS_KEY])
                                .jsonArray
                                .map { jsonSerdes.fromJson(it) as T }

                        PaginatedResponse(totalRecords = totalRecords, records = pagedRecords)
                    }

    private suspend inline fun <reified T : Entity> get(url: String, id: String): T =
            client.get<T>("$url/$id") {
                requestAuthProvider()
            }

    private suspend fun <T : RequestEntity> createFromEntity(url: String, entity: T): String =
            client.post(url) {
                requestAuthProvider()
                contentType(ContentType.Application.Json)
                body = entity
            }

    private suspend fun deleteById(url: String, id: String) =
            client.delete<Unit>("$url/$id") {
                requestAuthProvider()
            }

    private suspend fun updateById(url: String, id: String, entity: Entity) =
            client.put<Unit>("$url/$id") {
                requestAuthProvider()
                contentType(ContentType.Application.Json)
                body = entity
            }

}
