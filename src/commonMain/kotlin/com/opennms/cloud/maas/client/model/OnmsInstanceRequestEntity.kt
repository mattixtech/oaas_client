package com.opennms.cloud.maas.client.model

import kotlinx.serialization.Serializable

@Serializable
data class OnmsInstanceRequestEntity(val name: String) : RequestEntity
