package com.example.juzzics.common.base.extensions

import androidx.compose.runtime.MutableState
import com.example.juzzics.common.base.viewModel.State
import com.example.juzzics.common.base.viewModel.BaseViewModel

/** post change to a mutableState of something,
 *  for example data class that contains TextField values. */
inline fun <T> MutableState<T>.postChange(copy: T.() -> T) {
    this.value = copy(this.value)
}

/** casts state from the BaseViewModel.statesList to it's real data Type and returns it's value.
 * @see BaseViewModel.stateList
 * */
@Suppress("UNCHECKED_CAST")
fun <T> MutableState<State<Any>>.takeAs(): T? = try {
    this.value.data as T
} catch (e: Exception) {
    null
}