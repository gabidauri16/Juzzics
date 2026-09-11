package com.example.juzzics.common.base.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import com.example.juzzics.common.base.extensions.takeAs

typealias BaseState = Map<String, MutableState<State<Any>>>


// ---------------------- Generic Type Composable State Getters  ----------------------

/** gets state by stateKey in Composable functions */
@Composable
fun <T> BaseState.getState(stateKey: String) =
    remember { this[stateKey] }?.takeAs<T>()

/** gets state by stateKey in Composable functions if in context of [BaseState]*/
context (baseState: BaseState)
@Composable
fun <T> String.state(): T? = remember { baseState[this@state] }?.takeAs<T>()

/** gets state by calling invoke() operator on a stateKey in Composable functions if in context of [BaseState]*/
context (baseState: BaseState)
@Composable
operator fun <T> String.invoke(): T? = remember { baseState[this@invoke] }?.takeAs<T>()


// ---------------------- String Type State Getters  ----------------------

/** gets state by calling on a stateKey in Composable functions if in context of [BaseState]
 * @return value or Blank string if value is null
 *
 * @exception DOES_NOT use with invoke() or any state getter*/
context (baseState: BaseState)
@Composable
fun String.stateOrBlank(): String =
    remember { baseState[this@stateOrBlank] }?.takeAs<String>() ?: ""

/** gets State<String> by calling [!] or - not() operator on a stateKey in Composable functions if in context of [BaseState]
 * @return value or Blank string if value is null
 *
 * @sample !STATE_KEY_STRING
 * @exception DOES_NOT use with invoke() or any state getter*/
context (baseState: BaseState)
@Composable
operator fun String.not(): String = remember { baseState[this@not] }?.takeAs<String>() ?: ""


// ---------------------- Long Type State Getters  ----------------------

/** gets state by calling on a stateKey in Composable functions if in context of [BaseState]
 * @return value or 0L if value is null
 * @exception DOES_NOT use with invoke() or any state getter*/
context (baseState: BaseState)
@Composable
fun String.stateOrZero(): Long = remember { baseState[this@stateOrZero] }?.takeAs<Long>() ?: 0L


// ---------------------- Boolean Type State Getters  ----------------------

/** gets state by calling on a stateKey in Composable functions if in context of [BaseState]
 * @return value or false if value is null
 * @exception DOES_NOT use with invoke() or any state getter*/
context (baseState: BaseState)
@Composable
fun String.stateOrFalse(): Boolean =
    remember { baseState[this@stateOrFalse] }?.takeAs<Boolean>() ?: false



// ---------------------- Generic Type NonComposable State Getters  ----------------------

/** gets state by stateKey in normal functions*/
fun <T> BaseState.getStateValue(stateKey: String) =
    this[stateKey]?.takeAs<T>()

/** gets state by stateKey in normal functions if in context of [BaseState]*/
context (baseState: BaseState)
fun <T> String.stateValue() = baseState[this@stateValue]?.takeAs<T>()