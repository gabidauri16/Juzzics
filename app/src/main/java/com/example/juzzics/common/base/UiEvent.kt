package com.example.juzzics.common.base

interface UiEvent {
    data class Message(val msg: String) : UiEvent
    data class Loading(val loading: Boolean = true) : UiEvent
}