package com.opennms.cloud.maas.client.model

internal actual typealias PlatformIterable<T> = Array<T>

internal actual fun <T> List<T>.asPlatformIterable() = toTypedArray()
