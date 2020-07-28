package com.opennms.cloud.maas.client

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

class JsMaasPortalClient(private val client: CoroutineMaasPortalClient) {

    fun getInstances() = GlobalScope.promise { client.getInstances() }

}
