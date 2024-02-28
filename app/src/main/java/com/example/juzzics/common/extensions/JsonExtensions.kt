package com.example.juzzics.common.extensions

import com.google.gson.Gson

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}