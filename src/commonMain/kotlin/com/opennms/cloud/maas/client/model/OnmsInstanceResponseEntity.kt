package com.opennms.cloud.maas.client.model

import kotlinx.serialization.Serializable

@Serializable
data class OnmsInstanceResponseEntity(
        val organizationName: String,
        val name: String,
        val managed: Boolean,
        val id: String
)
