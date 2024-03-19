package com.example.juzzics.common.base.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/** used to separate viewModel function's logic.
 * subtypes are able to states same way as if it was done from ViewModel itself */
interface ViewModelsLogic

/** to be able to emit event from [ViewModelsLogic]*/
context (BaseViewModel, ViewModelsLogic)
fun <T : UiEvent> T.emit() = viewModelScope.launch { uiEvent.emit(this@emit) }