package com.opennms.cloud.maas.client

class MaasPortalClientBuilder : AbstractClientBuilder<JvmMaasPortalClient>() {

    override fun build(): JvmMaasPortalClient {
        validate()

        return JvmMaasPortalClient(CoroutineMaasPortalClient(environment, authenticationMethod, organization))
    }

}