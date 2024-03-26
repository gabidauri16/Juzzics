package com.example.juzzics.common.base.viewModel

/** use for sending events from ViewModel to the Screen */
interface UiEvent {
    data class Message(val msg: String) : UiEvent
    data class Loading(val loading: Boolean = true) : UiEvent
}