package com.example.juzzics.common.base

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juzzics.common.base.extensions.postChange
import com.example.juzzics.common.base.extensions.takeAs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel(
    val states: Map<String, Any>
) : ViewModel() {
    protected val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    /** for Compose */
    val stateList = states.map { it.key to it.value.createState() }.toMap()

    abstract fun onAction(action: Action)

    /** creates MutableState<ViewState<EachModel>> for each element of the states.
     * for Compose */
    private inline fun <reified T : Any?> T.createState(): MutableState<ViewState<T>> =
        mutableStateOf(ViewState())

    /** launches coroutine in viewModelScope. used in combination with call function to make requestCalls.
     *  also handles to emit message(or Error) and loading Actions.
     *  @see call
     * */
    protected fun launch(
        emitLoadingAction: Boolean = true,
        emitErrorMsgAction: Boolean = false,
        propagateCancellationException: Boolean = false,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        onException: ((Exception) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch {
            onStart?.invoke()
            if (emitLoadingAction) emitEvent(UiEvent.Loading())
            try {
                block.invoke(this)
            } catch (e: Exception) {
                onException?.invoke(e)
                if (emitLoadingAction) emitEvent(UiEvent.Loading(false))
                handleException(e, emitErrorMsgAction, propagateCancellationException)
            }
            if (emitLoadingAction) emitEvent(UiEvent.Loading(false))
            onFinish?.invoke()
        }
    }

    private fun handleException(
        e: Exception,
        emitErrorMsgAction: Boolean,
        propagateCancellationException: Boolean
    ) {
        e.printStackTrace()
        fun emitMsg(msg: String) {
            if (emitErrorMsgAction) UiEvent.Message(msg).emit()
        }
        when (e) {
            is CancellationException -> emitMsg("Cancellation Exception")
            is IOException -> emitMsg(e.message ?: "network Error")
            else -> emitMsg(e.message ?: "some error occurred")
        }
        if (propagateCancellationException) throw CancellationException()
    }

    /** makes serviceCall and updates corresponding State.
     *
     *  parameter: response - takes serviceCall that returns Result<YourServiceCallResponseModel>.
     *  parameter: stateIndex - takes index of corresponding ViewState from States.
     *  @see states
     * */
    protected suspend inline fun <reified T : Any?> call(
        response: Result<T>, stateKey: String,
        onError: (Throwable?) -> Unit = {},
        onSuccess: (Result<T>) -> Unit = {},
    ) {
        if (response.isSuccess) {
            onSuccess.invoke(response)
            stateList[stateKey]?.postChange { copy(data = response.getOrNull()) }
        } else {
            onError.invoke(response.exceptionOrNull())
            stateList[stateKey]?.postChange { copy(data = null) }
            response.exceptionOrNull()?.message?.let { emitEvent(UiEvent.Message(it)) }
        }
    }

    fun <T> updateState(keyString: String, value: T) {
        stateList[keyString]?.postChange { copy(value) }
    }

    fun <T> String.setData(value: T) {
        stateList[this]?.postChange { copy(value) }
    }

    /** launches a coroutine and emits Action */
    protected fun <T : UiEvent> T.emit() = viewModelScope.launch { _uiEvent.emit(this@emit) }

    /** use inside coroutineScope to emit Action */
    protected suspend fun emitEvent(uiEvent: UiEvent) = this._uiEvent.emit(uiEvent)

    /** returns state data by stateIndex */
    @Composable
    fun <T> getData(stateKey: String) = remember { this.stateList[stateKey] }?.takeAs<T>()
    fun <T> data(stateKey: String) = stateList[stateKey]?.takeAs<T>()
    fun <T> String.typeOf() = stateList[this]?.takeAs<T>()

    @SuppressLint("ComposableNaming")
    @Composable
    fun onEvent(onCollect: suspend (UiEvent) -> Unit) {
        LaunchedEffect(true) {
            uiEvent.collect {
                onCollect(it)
            }
        }
    }
}
