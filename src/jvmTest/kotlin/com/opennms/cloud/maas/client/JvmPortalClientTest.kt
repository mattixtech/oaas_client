package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.TokenAuthenticationMethod
import org.junit.Test

class JvmPortalClientTest {

    @Test
    fun `can get instances`() {
        val client = MaasPortalClientBuilder()
                .withOrganization("matt")
                .withEnvironment(Environment.DEV)
                .withAuthenticationMethod(TokenAuthenticationMethod(System.getenv("BEARER_TOKEN")))
                .build()
        client.getOnmsinstances().get().also { println(it) }
    }

}
