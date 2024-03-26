package com.example.juzzics.features.home.vm.logics

import com.example.juzzics.features.home.vm.HomeVM

fun HomeVM.makeCall() = launch { call(Result.success("Giorgi Gabidauri"), HomeVM.TEST) }
    .also { HomeVM.ToastMsg("got Name").emit() }
