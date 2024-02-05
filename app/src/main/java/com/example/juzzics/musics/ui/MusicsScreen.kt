@file:OptIn(ExperimentalWearMaterialApi::class)

package com.example.juzzics.musics.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.juzzics.musics.domain.model.MusicFileModel
import org.koin.androidx.compose.koinViewModel
import java.util.EnumSet
import coil.compose.AsyncImage
import com.example.juzzics.common.base.BaseActionsHandler
import com.example.juzzics.common.base.extensions.isNotNull


@OptIn(
    ExperimentalMotionApi::class, ExperimentalFoundationApi::class,
    ExperimentalWearMaterialApi::class
)
@Composable
fun MusicsScreen(vm: MusicVM = koinViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val lazyListState = rememberLazyListState()
            LaunchedEffect(true) {
                lazyListState.animateScrollToItem(
                    vm.data<Int>(stateKey = MusicVM.Scroll_POSISION) ?: 0
                )
            }
            vm.onEvent {
                when (it) {
                    is MusicVM.ScrollToPositionUiEvent -> lazyListState.animateScrollToItem(it.position)
                }
            }

            var componentHeight by remember { mutableStateOf(1000f) }
            val swipeAbleState = rememberSwipeableState("Bottom")
            val anchors = mapOf(0f to "Bottom", componentHeight to "Top")

            val motionProgress = (swipeAbleState.offset.value / componentHeight)
            val context = LocalContext.current


            val motionScene = remember {
                context.resources
                    .openRawResource(R.raw.music_motion_sceen)
                    .readBytes()
                    .decodeToString()
            }
            MotionLayout(
                motionScene = MotionScene(content = motionScene),
                progress = motionProgress,
                debug = EnumSet.of(MotionLayoutDebugFlags.NONE),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .onSizeChanged { size ->
                        componentHeight = size.height.toFloat()
                    }
            ) {
                MusicList(
                    modifier = Modifier.layoutId("music_list"),
                    musicFiles = vm.getData<List<MusicFileModel>>(MusicVM.MUSIC_LIST),
                    listState = lazyListState
                ) { vm.onAction(MusicVM.PlayMusicAction(it)) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .swipeable(
                            state = swipeAbleState,
                            anchors = anchors,
                            reverseDirection = true,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Vertical
                        )
                        .layoutId("box")
                )
                val clickedMusic = vm.getData<MusicFileModel>(MusicVM.CLICKED_MUSIC)

                Text(
                    text = "Now Playing:",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .layoutId("now_playing")
                )
                Text(
                    text = clickedMusic?.title ?: "No song playing",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .basicMarquee(
                            animationMode = MarqueeAnimationMode.Immediately,
                            delayMillis = 1000
                        )
                        .layoutId("music_name"),
                    fontSize = 18.sp
                )
                AsyncImage(
                    model = clickedMusic?.icon,
                    contentDescription = "music icon",
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier.layoutId("icon"),
                    contentScale = ContentScale.Fit
                    //todo: texts are set after image and they pushed to the right?
                )
                FloatingActionButton(
                    onClick = { vm.onAction(MusicVM.PlayOrPauseAction) },
                    modifier = Modifier
                        .padding(8.dp)
                        .layoutId("play_or_stop"),
                    shape = CircleShape
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val isPlaying = vm.getData(MusicVM.IS_PLAYING) ?: false
                        Icon(
                            if (isPlaying) Icons.Filled.Warning else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play"
                        )
                        Text(text = if (isPlaying) "Pause" else "Play")
                    }
                }
//                Box(
//                    Modifier
////                        .weight(0.2f)
//                ) {
//                    NowPlaying(
//                        mediaPlayer =      vm.getData(MusicVM.MEDIA_PLAYER),
//                        isPlaying =        vm.getData(MusicVM.IS_PLAYING) ?: false,
//                        currentSongTitle = vm.getData<MusicFileModel>(MusicVM.CLICKED_MUSIC)?.title,
//                        playOrPause = {       vm.onAction(MusicVM.PlayOrPauseAction)                   },
//                        next = {              vm.onAction(MusicVM.PlayNextAction)                         },
//                        prev = {           vm.onAction(MusicVM.PlayPrevAction)                              },
//                        seekTo = {         vm.onAction(MusicVM.SeekToAction(it))                    },
//                    )
//                }
            }
        }
    }
}

@Composable
fun MusicList(
    modifier: Modifier = Modifier,
    musicFiles: List<MusicFileModel>?,
    listState: LazyListState,
    onItemClick: (MusicFileModel) -> Unit
) {
    LazyColumn(modifier = modifier, state = listState) {
        musicFiles?.let {
            items(it) { musicFile ->
                MusicListItem(musicFile, onItemClick)
            }
        }
    }
}