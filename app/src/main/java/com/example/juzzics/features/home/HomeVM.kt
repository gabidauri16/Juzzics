package com.example.juzzics.features.home

import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.BaseViewModel
import com.example.juzzics.common.base.viewModel.ViewState

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

    private fun makeCall() {
        launch { call(Result.success("Giorgi Gabidauri"), TEST) }
    }

    object CallAction : Action

}