package com.example.juzzics.common.util.converters

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

/*** transforms percentage of the screen width in dp */
@Composable
fun Int.ofWidth(): Dp {
    val context = LocalContext.current
    val screenWidth = LocalDensity.current.run {
        context.resources.displayMetrics.widthPixels.toDp()
    }
    return (screenWidth * this / 100f)
}

/*** transforms percentage of the screen height in dp */
@Composable
fun Int.ofHeight(): Dp {
    val context = LocalContext.current
    val screenWidth = LocalDensity.current.run {
        context.resources.displayMetrics.heightPixels.toDp()
    }
    return (screenWidth * this / 100f)
}

/*** transforms percentage of the screen width in dp */
@Composable
fun Float.ofWidth(): Dp {
    val context = LocalContext.current
    val screenWidth = LocalDensity.current.run {
        context.resources.displayMetrics.widthPixels.toDp()
    }
    return (screenWidth * this / 100f)
}

/*** transforms percentage of the screen height in dp */
@Composable
fun Float.ofHeight(): Dp {
    val context = LocalContext.current
    val screenWidth = LocalDensity.current.run {
        context.resources.displayMetrics.heightPixels.toDp()
    }
    return (screenWidth * this / 100f)
}