package com.example.juzzics.features.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.juzzics.common.base.BaseHandler
import com.example.juzzics.common.base.extensions.with2
import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.State
import com.example.juzzics.common.base.viewModel.UiEvent
import com.example.juzzics.common.base.viewModel.listen
import com.example.juzzics.common.base.viewModel.not
import com.example.juzzics.features.home.vm.HomeVM
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeScreen(
    states: Map<String, MutableState<State<Any>>>,
    uiEvent: SharedFlow<UiEvent>,
    onAction: (Action) -> Unit
) {
    with2(first = states, second = HomeVM) {
        uiEvent.BaseHandler {
            val context = LocalContext.current
            uiEvent.listen {
                when (it) {
                    is HomeVM.ToastMsg -> {
                        Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            Surface {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column {
                        Text(text = !TEST, modifier = Modifier.clickable {
                            onAction(HomeVM.CallAction)
                        })
//                        Spacer(modifier = Modifier.height(30.dp))
//                        TextField(value = TEXT_FIELD_VALUE.stateOrBlank(), onValueChange = {
//                            onAction(HomeVM.UpdateTextFieldValueAction(it))
//                        })
                    }
                }
            }
        }
    }
}