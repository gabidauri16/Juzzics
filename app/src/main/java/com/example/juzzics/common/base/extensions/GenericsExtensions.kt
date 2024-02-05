package com.example.juzzics.common.base.extensions

fun <T> T?.isNotNull() = this != null

fun <T> T?.isNull() = this == null
