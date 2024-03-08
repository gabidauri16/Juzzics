package com.example.juzzics.common.base.viewModel

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.SharedFlow


/**use to listen [UiEvent]s in Composable*/
@SuppressLint("ComposableNaming")
@Composable
fun SharedFlow<UiEvent>.listen(onCollect: suspend (UiEvent) -> Unit) {
    LaunchedEffect(true) {
        collect {
            onCollect(it)
        }
    }
}