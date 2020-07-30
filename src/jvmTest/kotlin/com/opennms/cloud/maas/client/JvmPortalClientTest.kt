package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.TokenAuthenticationMethod
import org.junit.Ignore
import org.junit.Test

class JvmPortalClientTest {

    @Test
    @Ignore
    fun `can get instances`() {
        val client = MaasPortalClientBuilder()
                .withOrganization("matt")
                .withEnvironment(Environment.DEV)
                .withAuthenticationMethod(TokenAuthenticationMethod(System.getenv("BEARER_TOKEN")))
                .build()
        client.read.onmsInstances().get().let { println(it) }
    }

}
