package com.example.juzzics.features.home.vm

import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.BaseViewModel
import com.example.juzzics.common.base.viewModel.State
import com.example.juzzics.common.base.viewModel.UiEvent
import com.example.juzzics.features.home.vm.logics.makeCall

class HomeVM : BaseViewModel(
    states = mapOf(
        TEST to State("make a call"),
        TEXT_FIELD_VALUE to State("gabo")
    )
) {
    companion object {
        const val TEST = "test"
        const val TEXT_FIELD_VALUE = "textFieldValue"
    }

    override fun onAction(action: Action) {
        when (action) {
            is CallAction -> makeCall()
            is UpdateTextFieldValueAction -> TEXT_FIELD_VALUE(action.value)
        }
    }

    object CallAction : Action
    data class UpdateTextFieldValueAction(val value: String) : Action
    data class ToastMsg(val msg: String) : UiEvent
}