package com.opennms.cloud.maas.client.auth

class TokenAuthenticationMethod(token: String) : AuthenticationMethod {

    override val authenticationToken: String = token

}