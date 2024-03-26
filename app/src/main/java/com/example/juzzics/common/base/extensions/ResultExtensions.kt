package com.example.juzzics.common.base.extensions

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

inline fun <R, T> Result<List<T>>.mapList(transform: (value: T) -> R): Result<ImmutableList<R>> =
    this.map { list -> list.map { item -> transform(item) }.toImmutableList() }