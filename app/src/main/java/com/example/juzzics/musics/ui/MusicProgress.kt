package com.example.juzzics.musics.ui

import android.media.MediaPlayer
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay


@Composable
fun MusicProgress(modifier: Modifier, mediaPlayer: MediaPlayer?, seekTo: (Float) -> Unit) {
    val progress = remember { mutableFloatStateOf(0f) }
    LaunchedEffect(key1 = mediaPlayer) {
        while (mediaPlayer?.isPlaying == true) {
            progress.value = mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration.toFloat()
            delay(100)
        }
    }

    LinearProgressIndicator(
        progress = progress.value,
        modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures {
                    val clickPosition = it.x
                    progress.value = clickPosition / size.width
                    seekTo(progress.value)
                }
            }
    )
}