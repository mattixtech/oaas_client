package com.opennms.cloud.maas.client

class MaasPortalClientBuilder : AbstractClientBuilder<JsMaasPortalClient>() {

    override fun build(): JsMaasPortalClient {
        validate()

        return JsMaasPortalClient(CoroutineMaasPortalClient(environment, authenticationMethod, organization))
    }

}