package com.opennms.cloud.maas.client.auth

actual class UserPassAuthenticationMethod actual constructor(private val user: String, private val password: String) : AuthenticationMethod{
    actual override val authenticationToken: String
        get() = TODO("Not yet implemented")
}
