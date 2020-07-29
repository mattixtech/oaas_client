package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.AuthenticationMethod
import com.opennms.cloud.maas.client.model.OnmsInstance
import com.opennms.cloud.maas.client.model.PaginatedResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonElement

expect fun serializer(): JsonSerializer

@OptIn(UnstableDefault::class)
private val serdes = Json(JsonConfiguration.Default)

class CoroutineMaasPortalClient(
        environment: Environment,
        private val authenticationMethod: AuthenticationMethod,
        private val organization: String
) {

    private val baseUrl = "${environment.baseUrl}/api/v1/portal"
    private val authToken by lazy { authenticationMethod.authenticationToken }
    private val client = HttpClient() {
        install(JsonFeature) {
            serializer = serializer()
        }
    }
    private val provideBearer: HttpRequestBuilder.() -> Unit = { header("authorization", authToken) }

    // TODO: Clean this up
    suspend fun getOnmsInstances(): PaginatedResponse<OnmsInstance> = getJson(urlForOrgEndpoint("onms-instance")).let { jsonMap ->
        val onmsInstances = jsonMap["pagedRecords"]!!.jsonArray.map { serdes.fromJson(OnmsInstance.serializer(), it) }
        PaginatedResponse(totalRecords = jsonMap["totalRecords"]!!.primitive.int, pagedRecords = onmsInstances)
    }

    private fun urlForOrgEndpoint(endpoint: String) = "$baseUrl/$organization/$endpoint"

    private suspend inline fun getJson(url: String): Map<String, JsonElement> {
        return client.get(url, provideBearer)
    }

}
