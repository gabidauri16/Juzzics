package com.example.juzzics.common.base.extensions

fun <T> T?.isNotNull() = this != null

fun <T> T?.isNull() = this == null

fun <T> T?.returnIfNull(block: () -> T): T = this ?: block()

fun <T> T?.doIfNull(block: () -> Unit) = this ?: block()
