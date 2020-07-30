package com.opennms.cloud.maas.client.model

expect class PlatformIterable<T>
internal expect fun <T> List<T>.asPlatformIterable(): PlatformIterable<T>
