package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.AuthenticationMethod

abstract class AbstractClientBuilder<T> {

    protected lateinit var environment: Environment
    protected lateinit var authenticationMethod: AuthenticationMethod
    protected lateinit var organization: String

    fun withEnvironment(environment: Environment) = apply { this.environment = environment }
    fun withAuthenticationMethod(authenticationMethod: AuthenticationMethod) = apply { this.authenticationMethod = authenticationMethod }
    fun withOrganization(organization: String) = apply { this.organization = organization }

    protected fun validate() {
        require(this::environment.isInitialized)
        require(this::authenticationMethod.isInitialized)
        require(this::organization.isInitialized)
    }
    
    abstract fun build(): T
}