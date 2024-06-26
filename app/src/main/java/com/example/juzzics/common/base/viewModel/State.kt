package com.example.juzzics.common.base.viewModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class State<T : Any?>(val data: @RawValue T? = null) : Parcelable