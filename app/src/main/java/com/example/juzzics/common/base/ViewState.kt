package com.example.juzzics.common.base

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ViewState<T : Any?>(val data: @RawValue T? = null) : Parcelable