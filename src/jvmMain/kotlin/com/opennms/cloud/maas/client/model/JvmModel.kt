package com.opennms.cloud.maas.client.model

internal actual typealias PlatformIterable<T> = java.util.ArrayList<T>

internal actual fun <T> List<T>.asPlatformIterable() = if (this is ArrayList<T>) this else ArrayList(this)
