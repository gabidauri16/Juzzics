package com.example.juzzics.common.base.extensions

import androidx.compose.runtime.Composable

/**gives Composable context of two passed objects*/
@Composable
fun <First, Second> with2(
    first: First,
    second: Second,
    block: @Composable context(First)Second.() -> Unit
) {
    block.invoke(first, second)
}

/**gives context of two passed objects*/

fun <First, Second> First.andWith(
    second: Second,
    block: context(First)Second.() -> Unit
) {
    block.invoke(this, second)
}