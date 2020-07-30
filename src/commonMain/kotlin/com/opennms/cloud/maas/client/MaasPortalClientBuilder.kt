package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.AuthenticationMethod
import kotlin.js.JsName

class MaasPortalClientBuilder {

    private lateinit var environment: Environment
    private lateinit var authenticationMethod: AuthenticationMethod
    private lateinit var organization: String

    @JsName("withEnvironment")
    fun withEnvironment(environment: Environment) = apply { this.environment = environment }

    @JsName("withAuthenticationMethod")
    fun withAuthenticationMethod(authenticationMethod: AuthenticationMethod) = apply { this.authenticationMethod = authenticationMethod }

    @JsName("withOrganization")
    fun withOrganization(organization: String) = apply { this.organization = organization }

    private fun validate() {
        require(this::environment.isInitialized) { "An environment is required" }
        require(this::authenticationMethod.isInitialized) { "An authentication method is required" }
        require(this::organization.isInitialized) { "An organization is required" }
    }

    fun build(): AsyncMaasPortalClient {
        validate()

        return AsyncMaasPortalClient(
                environment = environment,
                authenticationMethod = authenticationMethod,
                organization = organization
        )
    }

}
