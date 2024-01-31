package com.example.juzzics.musics.ui

import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowPlaying(
    mediaPlayer: MediaPlayer?,
    isPlaying: Boolean,
    currentSongTitle: String?,
    playOrPause: () -> Unit,
    next: () -> Unit,
    prev: () -> Unit,
    seekTo: (Float) -> Unit
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
        Text(
            text = "Now Playing:",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = currentSongTitle ?: "No song playing",
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .basicMarquee(animationMode = MarqueeAnimationMode.Immediately, delayMillis = 1000)
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.03f))
        MusicProgress(mediaPlayer = mediaPlayer, seekTo = seekTo)
        Spacer(modifier = Modifier.fillMaxHeight(0.03f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = { prev() },
                modifier = Modifier,
                shape = CircleShape
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Previous")
                    Text(text = "Prev")
                }
            }

            FloatingActionButton(
                onClick = { playOrPause() },
                modifier = Modifier,
                shape = CircleShape
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        if (isPlaying) Icons.Filled.Warning else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                    Text(text = if (isPlaying) "Pause" else "Play")
                }
            }

            FloatingActionButton(
                onClick = { next() },
                modifier = Modifier,
                shape = CircleShape
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Next")
                    Text(text = "Next")
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.034f))
    }
}