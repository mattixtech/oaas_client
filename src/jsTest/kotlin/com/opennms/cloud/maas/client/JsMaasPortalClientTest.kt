package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.TokenAuthenticationMethod
import kotlin.js.Promise
import kotlin.test.Test

// To access environment variables in NodeJS
external val process: dynamic

class JsMaasPortalClientTest {

    @Test
    fun canGetInstances(): Promise<String> {
        val client = MaasPortalClientBuilder()
                .withOrganization("matt")
                .withEnvironment(Environment.DEV)
                .withAuthenticationMethod(TokenAuthenticationMethod(process.env.BEARER_TOKEN.unsafeCast<String>()))
                .build()
        return client.getOnmsInstances().apply { then { println(it) } }
    }

}
