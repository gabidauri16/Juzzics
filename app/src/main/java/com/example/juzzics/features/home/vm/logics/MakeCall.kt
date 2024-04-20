package com.example.juzzics.features.home.vm.logics

import com.example.juzzics.features.home.vm.HomeVM
import com.example.juzzics.features.home.vm.HomeVM.Companion.TEST
import kotlinx.coroutines.delay

fun HomeVM.makeCall() = launch {
    call(
//    Result.success("Giorgi Gabidauri")
        getData(), TEST
    )
}
//    .also { HomeVM.ToastMsg("got Name").emit() }

var a = true
suspend fun getData(): Result<String> = runCatching {
    delay(2000L)
    if (a) {
        a = false
        "first Successful Data"
    } else {
        a = true
        "other Successful Data"
    }
}