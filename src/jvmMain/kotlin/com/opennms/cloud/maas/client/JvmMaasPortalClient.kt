package com.opennms.cloud.maas.client

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

class JvmMaasPortalClient(private val client: CoroutineMaasPortalClient) {

    fun getInstances() = GlobalScope.future { client.getInstances() }

}

