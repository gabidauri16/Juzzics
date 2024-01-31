package com.example.juzzics.musics.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.juzzics.musics.domain.model.MusicFileModel
import org.koin.androidx.compose.koinViewModel

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
            vm.onEvent {
                when (it) {
                    is MusicVM.ScrollToPositionUiEvent -> lazyListState.animateScrollToItem(it.position)
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                MusicList(
                    musicFiles = vm.getData<List<MusicFileModel>>(stateKey = MusicVM.MUSIC_LIST)
                        ?: emptyList(),
                    listState = lazyListState
                ) { vm.onAction(MusicVM.PlayMusicAction(it)) }
            }
            Box(
                Modifier.weight(0.2f)
            ) {
                NowPlaying(
                    mediaPlayer = vm.getData(MusicVM.MEDIA_PLAYER),
                    isPlaying = vm.getData(MusicVM.IS_PLAYING) ?: false,
                    currentSongTitle = vm.getData<MusicFileModel>(MusicVM.CLICKED_MUSIC)?.title,
                    playOrPause = { vm.onAction(MusicVM.PlayOrPauseAction) },
                    next = { vm.onAction(MusicVM.PlayNextAction) },
                    prev = { vm.onAction(MusicVM.PlayPrevAction) },
                    seekTo = { vm.onAction(MusicVM.SeekToAction(it)) }
                )
            }
        }
    }
}

@Composable
fun MusicList(
    musicFiles: List<MusicFileModel>,
    listState: LazyListState,
    onItemClick: (MusicFileModel) -> Unit
) {
    LazyColumn(state = listState) {
        items(musicFiles) { musicFile ->
            MusicListItem(musicFile, onItemClick)
        }
    }
}