package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.AuthenticationMethod
import kotlin.js.JsName

abstract class AbstractClientBuilder<T> {

    protected lateinit var environment: Environment
    protected lateinit var authenticationMethod: AuthenticationMethod
    protected lateinit var organization: String

    @JsName("withEnvironment")
    fun withEnvironment(environment: Environment) = apply { this.environment = environment }

    @JsName("withAuthenticationMethod")
    fun withAuthenticationMethod(authenticationMethod: AuthenticationMethod) = apply { this.authenticationMethod = authenticationMethod }

    @JsName("withOrganization")
    fun withOrganization(organization: String) = apply { this.organization = organization }

    protected fun validate() {
        require(this::environment.isInitialized)
        require(this::authenticationMethod.isInitialized)
        require(this::organization.isInitialized)
    }

    abstract fun build(): T
}