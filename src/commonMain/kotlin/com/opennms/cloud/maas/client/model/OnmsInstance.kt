package com.opennms.cloud.maas.client.model

import kotlinx.serialization.Serializable

@Serializable
data class OnmsInstance(
        val organizationName: String,
        val name: String,
        val managed: Boolean,
        val id: String
)
