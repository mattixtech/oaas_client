package com.opennms.cloud.maas.client.auth

expect class UserPassAuthenticationMethod(user: String, password: String) : AuthenticationMethod {

    override val authenticationToken: String

}
