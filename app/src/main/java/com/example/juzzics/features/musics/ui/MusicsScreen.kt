@file:OptIn(ExperimentalWearMaterialApi::class)

package com.example.juzzics.features.musics.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
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
import coil.compose.AsyncImage
import com.example.juzzics.R
import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.UiEvent
import com.example.juzzics.common.base.viewModel.ViewState
import com.example.juzzics.common.base.extensions.returnIfNull
import com.example.juzzics.common.base.extensions.with2
import com.example.juzzics.common.base.viewModel.listen
import com.example.juzzics.common.base.viewModel.state
import com.example.juzzics.common.uiComponents.SimpleFAB
import com.example.juzzics.features.musics.ui.model.MusicFileUi
import kotlinx.coroutines.flow.SharedFlow
import java.util.EnumSet
@Composable
fun MusicList(
    modifier: Modifier = Modifier,
    musicFiles: List<MusicFileUi>?,
    listState: LazyListState,
    onItemClick: (MusicFileUi) -> Unit
) {
    LazyColumn(modifier = modifier, state = listState) {
        musicFiles?.let {
            items(it) { musicFile ->
                MusicListItem(musicFile, onItemClick)
            }
        }
    }
}

@OptIn(
    ExperimentalMotionApi::class, ExperimentalFoundationApi::class,
    ExperimentalWearMaterialApi::class
)
@Composable
fun MusicsScreen(
    states: Map<String, MutableState<ViewState<Any>>>,
    uiEvent: SharedFlow<UiEvent>,
    onAction: (Action) -> Unit
) {
    with2(states, MusicVM) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val lazyListState = rememberLazyListState()
                uiEvent.listen {
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
                        .onSizeChanged { size -> componentHeight = size.height.toFloat() }
                ) {
                    val clickedMusic = CLICKED_MUSIC.state<MusicFileUi>()
                    val isPlaying = IS_PLAYING.state() ?: false

                    MusicList(
                        modifier = Modifier.layoutId("music_list"),
                        musicFiles = MUSIC_LIST.state(),
                        listState = lazyListState
                    ) { onAction(MusicVM.PlayMusicAction(it)) }
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
                    Text(
                        text = "Now Playing:",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .layoutId("now_playing"),
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
                        model = clickedMusic?.icon.returnIfNull { R.drawable.ic_launcher_foreground },
                        contentDescription = "music icon",
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        modifier = Modifier
                            .layoutId("icon")
                            .aspectRatio(
                                //if (swipeAbleState.currentValue == "Bottom") 2f else 0.8f,//todo: animate
                                1f,
                                true
                            ),
                        contentScale = ContentScale.Fit,
                    )
                    MusicProgress(
                        modifier = Modifier.layoutId("music_progress"),
                        mediaPlayer = MEDIA_PLAYER.state(),
                        seekTo = { onAction(MusicVM.SeekToAction(it)) })

                    SimpleFAB(
                        modifier = Modifier.layoutId("bt_prev"),
                        text = "Prev",
                        image = Icons.AutoMirrored.Filled.KeyboardArrowLeft
                    ) { onAction(MusicVM.PlayPrevAction) }

                    SimpleFAB(
                        modifier = Modifier.layoutId("bt_next"),
                        text = "Next",
                        image = Icons.AutoMirrored.Filled.KeyboardArrowRight
                    ) { onAction(MusicVM.PlayNextAction) }

                    SimpleFAB(
                        modifier = Modifier.layoutId("play_or_stop"),
                        image = if (isPlaying) Icons.Filled.Warning else Icons.Filled.PlayArrow,
                        text = if (isPlaying) "Pause" else "Play"
                    ) { onAction(MusicVM.PlayOrPauseAction) }
                }
            }
        }
    }
}