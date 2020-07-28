package com.opennms.cloud.maas.client.auth

interface AuthenticationMethod {
    val authenticationToken: String
}