package com.example.juzzics.common.base.extensions

fun Long.toMusicDuration(): String {
    val hours = this / (60 * 60 * 1000)
    val remainingMinutes = this % (60 * 60 * 1000)
    val minutes = remainingMinutes / (60 * 1000)
    val seconds = remainingMinutes % (60 * 1000) / 1000

    val parts = mutableListOf<String>()
    if (hours > 0) parts.add("$hours h")
    if (minutes > 0 || hours > 0) parts.add("$minutes m")
    if (seconds > 0 || minutes > 0 || hours > 0) parts.add("$seconds s")

    return parts.joinToString(", ")
}