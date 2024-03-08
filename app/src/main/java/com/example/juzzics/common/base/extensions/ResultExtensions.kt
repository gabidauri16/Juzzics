package com.example.juzzics.common.base.extensions

inline fun <R, T> Result<List<T>>.mapList(transform: (value: T) -> R): Result<List<R>> =
    this.map { list -> list.map { item -> transform(item) } }