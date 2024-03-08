package com.example.juzzics.common.base.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import com.example.juzzics.common.base.extensions.takeAs

typealias BaseState = Map<String, MutableState<ViewState<Any>>>

/** gets state by stateKey in Composable functions */

@Composable
fun <T> Map<String, MutableState<ViewState<Any>>>.getState(stateKey: String) =
    remember { this[stateKey]?.takeAs<T>() }

/** gets state by stateKey in Composable functions if in context of [BaseState]*/
context (BaseState)
@Composable
fun <T> String.state() = remember { this@BaseState[this]?.takeAs<T>() }


/** gets state by stateKey in normal functions*/
fun <T> Map<String, MutableState<ViewState<Any>>>.getStateValue(stateKey: String) =
    this[stateKey]?.takeAs<T>()

/** gets state by stateKey in normal functions if in context of [BaseState]*/
context (BaseState)
fun <T> String.stateValue() = this@BaseState[this]?.takeAs<T>()