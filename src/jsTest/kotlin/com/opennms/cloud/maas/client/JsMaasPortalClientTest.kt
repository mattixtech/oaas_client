package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.TokenAuthenticationMethod
import kotlin.js.Promise
import kotlin.test.Test

private val token = "TODO"

class JsMaasPortalClientTest {

    @Test
    fun canGetInstances(): Promise<String> {
        val client = MaasPortalClientBuilder()
                .withOrganization("matt")
                .withEnvironment(Environment.DEV)
                .withAuthenticationMethod(TokenAuthenticationMethod(token))
                .build()
        return client.getInstances().apply { then { println(it) } }
    }

}