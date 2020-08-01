package com.opennms.cloud.maas.client.model

import kotlinx.serialization.Serializable
import kotlin.js.JsName

@Serializable
data class OnmsInstanceEntity(
        val organizationName: String,
        val name: String,
        val managed: Boolean = false,
        val id: String
) : Entity {

    class Builder {
        private lateinit var organizationName: String
        private lateinit var name: String
        private lateinit var id: String

        @JsName("withOrganization")
        fun withOrganization(organizationName: String) = apply { this.organizationName = organizationName }

        @JsName("withName")
        fun withName(name: String) = apply { this.name = name }

        @JsName("withId")
        fun withId(id: String) = apply { this.id = id }

        private fun validate() {
            require(::organizationName.isInitialized) { "An organization is required" }
            require(::name.isInitialized) { "A name is required" }
            require(::id.isInitialized) { "An Id is required" }
        }

        fun build(): OnmsInstanceEntity {
            validate()

            return OnmsInstanceEntity(
                    organizationName = organizationName,
                    name = name,
                    id = id
            )
        }
    }

}
