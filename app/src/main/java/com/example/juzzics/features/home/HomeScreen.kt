package com.example.juzzics.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.juzzics.common.base.extensions.with2
import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.UiEvent
import com.example.juzzics.common.base.viewModel.ViewState
import com.example.juzzics.common.base.viewModel.state
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeScreen(
    states: Map<String, MutableState<ViewState<Any>>>,
    uiEvent: SharedFlow<UiEvent>,
    onAction: (Action) -> Unit
) {
    with2(first = states, second = HomeVM) {
        Surface {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val text = TEST.state<String>()
                Text(text = text ?: "Home Screen", modifier = Modifier.clickable {
                    onAction(HomeVM.CallAction)
                })
            }
        }
    }
}