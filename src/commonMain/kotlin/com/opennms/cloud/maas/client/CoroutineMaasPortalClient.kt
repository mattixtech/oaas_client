package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.AuthenticationMethod
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header

class CoroutineMaasPortalClient(
        environment: Environment,
        private val authenticationMethod: AuthenticationMethod,
        private val organization: String
) {

    private val baseUrl = "${environment.baseUrl}/api/v1/portal"
    private val authToken by lazy { authenticationMethod.authenticationToken }
    private val client = HttpClient()
    private val provideBearer: HttpRequestBuilder.() -> Unit = { header("authorization", authToken) }

    suspend fun getOnmsInstances(): String = get(urlForOrgEndpoint("onms-instance"))

    private fun urlForOrgEndpoint(endpoint: String) = "$baseUrl/$organization/$endpoint"

    private suspend inline fun <reified T: Any> get(url: String): T {
        return client.get(url, provideBearer)
    }

}
