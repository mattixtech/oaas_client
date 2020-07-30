package com.opennms.cloud.maas.client

import com.opennms.cloud.maas.client.auth.TokenAuthenticationMethod
import kotlin.js.Promise
import kotlin.test.Ignore
import kotlin.test.Test

// To access environment variables in NodeJS
external val process: dynamic

class JsMaasPortalClientTest {

    @Test
    @Ignore
    fun canGetInstances() {
        val client = MaasPortalClientBuilder()
                .withOrganization("matt")
                .withEnvironment(Environment.DEV)
                .withAuthenticationMethod(TokenAuthenticationMethod(process.env.BEARER_TOKEN.unsafeCast<String>()))
                .build()

        (client.read.onmsInstances() as Promise<*>).then {
            println(it)
        }
    }

}
