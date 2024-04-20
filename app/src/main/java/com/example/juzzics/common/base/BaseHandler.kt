package com.example.juzzics.common.base

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.juzzics.common.base.viewModel.UiEvent
import kotlinx.coroutines.flow.SharedFlow

/** handles showing message and showing loader. */
@Composable
fun SharedFlow<UiEvent>.BaseHandler(
    showLoader: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val loadingState = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = this@BaseHandler) {
        this@BaseHandler.collect {
            when (it) {
                is UiEvent.Message -> Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                is UiEvent.Loading -> if (showLoader) loadingState.value = it.loading
            }
        }
    }
    LoaderBox(loading = { loadingState.value }, content)
}

@Composable
fun LoaderBox(loading: () -> Boolean, content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = loading(),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight(0.5f),
            enter = fadeIn() + scaleIn(initialScale = 50f),
            exit = fadeOut() + scaleOut(targetScale = -10f)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopCenter),
                strokeCap = StrokeCap.Butt,
                strokeWidth = 2.dp,
                color = Color.Green,
                trackColor = Color.Blue
            )
        }
        AnimatedVisibility(
            visible = !loading(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            content()
        }
    }
}