package com.opennms.cloud.maas.client.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T : Any> constructor(val totalRecords: Int, val pagedRecords: List<T>)
