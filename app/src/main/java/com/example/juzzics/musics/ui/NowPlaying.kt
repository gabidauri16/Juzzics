package com.example.juzzics.musics.ui

import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.juzzics.R
import java.util.EnumSet

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMotionApi::class,
    ExperimentalWearMaterialApi::class
)
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
//
//    var componentHeight by remember { mutableStateOf(1000f) }
//    val swipeAbleState = rememberSwipeableState("Bottom")
//    val anchors = mapOf(0f to "Bottom", componentHeight to "Top")
//
//    val motionProgress = (swipeAbleState.offset.value / componentHeight)
//    val context = LocalContext.current
//
//
//    val motionScene = remember {
//        context.resources
//            .openRawResource(R.raw.music_motion_sceen)
//            .readBytes()
//            .decodeToString()
//    }
//    MotionLayout(
//        motionScene = MotionScene(content = motionScene),
//        progress = motionProgress,
//        debug = EnumSet.of(MotionLayoutDebugFlags.NONE),
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.primaryContainer)
//            .swipeable(
//                state = swipeAbleState,
//                anchors = anchors,
//                reverseDirection = true,
//                thresholds = { _, _ -> FractionalThreshold(0.3f) },
//                orientation = Orientation.Vertical
//            )
//            .onSizeChanged { size ->
//                componentHeight = size.height.toFloat()
//            }
//    ) {
        Text(
            text = "Now Playing:",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .layoutId("now_playing")
        )
        Text(
            text = currentSongTitle ?: "No song playing",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .basicMarquee(
                    animationMode = MarqueeAnimationMode.Immediately,
                    delayMillis = 1000
                )
                .layoutId("music_name"),
            fontSize = 18.sp
        )
        FloatingActionButton(
            onClick = { playOrPause() },
            modifier = Modifier
                .padding(8.dp)
                .layoutId("play_or_stop"),
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
//    }


//    Column(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
//        Text(
//            text = "Now Playing:",
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//        )
//        Text(
//            text = currentSongTitle ?: "No song playing",
//            modifier = Modifier
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//                .basicMarquee(animationMode = MarqueeAnimationMode.Immediately, delayMillis = 1000)
//        )
//        Spacer(modifier = Modifier.fillMaxHeight(0.03f))
//        MusicProgress(mediaPlayer = mediaPlayer, seekTo = seekTo)
//        Spacer(modifier = Modifier.fillMaxHeight(0.03f))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            FloatingActionButton(
//                onClick = { prev() },
//                modifier = Modifier,
//                shape = CircleShape
//            ) {
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Previous")
//                    Text(text = "Prev")
//                }
//            }
//
//            FloatingActionButton(
//                onClick = { playOrPause() },
//                modifier = Modifier,
//                shape = CircleShape
//            ) {
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Icon(
//                        if (isPlaying) Icons.Filled.Warning else Icons.Filled.PlayArrow,
//                        contentDescription = if (isPlaying) "Pause" else "Play"
//                    )
//                    Text(text = if (isPlaying) "Pause" else "Play")
//                }
//            }
//
//            FloatingActionButton(
//                onClick = { next() },
//                modifier = Modifier,
//                shape = CircleShape
//            ) {
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Next")
//                    Text(text = "Next")
//                }
//            }
//        }
//        Spacer(modifier = Modifier.fillMaxHeight(0.034f))
//    }
}