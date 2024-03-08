package com.example.juzzics.common.base.extensions

import androidx.compose.runtime.Composable

@Composable
fun <First, Second> with2(
    first: First,
    second: Second,
    block: @Composable context(First)Second.() -> Unit
) {
    block.invoke(first, second)
}

@Composable
fun <First, Second> First.andWith(
    second: Second,
    block: @Composable context(First)Second.() -> Unit
) {
    block.invoke(this, second)
}