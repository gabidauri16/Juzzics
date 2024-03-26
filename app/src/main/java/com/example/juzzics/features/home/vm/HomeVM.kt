package com.example.juzzics.features.home.vm

import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.BaseViewModel
import com.example.juzzics.common.base.viewModel.UiEvent
import com.example.juzzics.common.base.viewModel.ViewState
import com.example.juzzics.features.home.vm.logics.makeCall

class HomeVM : BaseViewModel(
    states = mapOf(
        TEST to ViewState<String>()
    )
) {
    companion object {
        const val TEST = "test"
    }

    override fun onAction(action: Action) {
        when (action) {
            is CallAction -> makeCall()
        }
    }

    object CallAction : Action
    data class ToastMsg(val msg: String) : UiEvent
}