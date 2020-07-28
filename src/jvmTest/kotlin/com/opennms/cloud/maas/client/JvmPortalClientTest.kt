package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.TokenAuthenticationMethod
import org.junit.Test

private val token = "TODO"

class JvmPortalClientTest {
    
    @Test
    fun `can get instances`() {
        val client = MaasPortalClientBuilder()
                .withOrganization("matt")
                .withEnvironment(Environment.DEV)
                .withAuthenticationMethod(TokenAuthenticationMethod(token))
                .build()
        client.getInstances().get().also { println(it) }
    }
    
}