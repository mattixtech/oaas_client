package com.opennms.cloud.maas.client.model

import kotlin.js.JsName

enum class SortOrder {
    ASC,
    DESC
}

data class ListQueryOptions(
        val orderByColumn: String? = null,
        val limit: Int? = null,
        val offset: Int? = null,
        val sortOrder: SortOrder? = null,
        val searchPrefix: String? = null,
        val searchField: String? = null
) {

    init {
        if (searchField != null) requireNotNull(searchPrefix) { "A search prefix must be provided when search field is set" }
        if (searchPrefix != null) requireNotNull(searchField) { "A search field must be provided when a search prefix is set" }
    }
    
    class Builder {
        private var orderByColumn: String? = null
        private var limit: Int? = null
        private var offset: Int? = null
        private var sortOrder: SortOrder? = null
        private var searchPrefix: String? = null
        private var searchField: String? = null

        @JsName("withOrderByColumn")
        fun withOrderByColumn(orderByColumn: String) = apply { this.orderByColumn = orderByColumn }

        @JsName("withLimit")
        fun withLimit(limit: Int) = apply { this.limit = limit }

        @JsName("withOffset")
        fun withOffset(offset: Int) = apply { this.offset = offset }

        @JsName("withSortOrder")
        fun withSortOrder(sortOrder: SortOrder) = apply { this.sortOrder = sortOrder }

        @JsName("withSearchPrefix")
        fun withSearchPrefix(searchPrefix: String) = apply { this.searchPrefix = searchPrefix }

        @JsName("withSearchField")
        fun withSearchField(searchField: String) = apply { this.searchField = searchField }

        private fun validate() {
        }

        fun build(): ListQueryOptions {
            validate()

            return ListQueryOptions(
                    orderByColumn = orderByColumn,
                    limit = limit,
                    offset = offset,
                    sortOrder = sortOrder,
                    searchPrefix = searchPrefix,
                    searchField = searchField
            )
        }
    }

}