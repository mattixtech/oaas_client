package com.opennms.cloud.maas.client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal const val TOTAL_RECORDS_KEY = "totalRecords"
internal const val PAGED_RECORDS_KEY = "pagedRecords"

@Serializable
data class PaginatedResponse<T : Any>(
        val totalRecords: Int,
        @SerialName(PAGED_RECORDS_KEY)
        private val records: List<T>) {

    val pagedRecords get() = records.asPlatformIterable()

}
