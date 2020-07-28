package com.opennms.cloud.maas.client

enum class Environment(val baseUrl: String) {
    PROD("https://cloud.opennms.com"),
    QA("https://qa.cloud.opennms.com"),
    DEV("https://dev.cloud.opennms.com")
}
