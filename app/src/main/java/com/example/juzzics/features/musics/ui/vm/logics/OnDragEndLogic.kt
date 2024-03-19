package com.example.juzzics.features.musics.ui.vm.logics

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.juzzics.features.musics.ui.model.MusicFileUi
import com.example.juzzics.features.musics.ui.vm.MusicVM
import kotlinx.collections.immutable.toImmutableList

fun MusicVM.onDragEnd(list: SnapshotStateList<MusicFileUi>) {
    MusicVM.MUSIC_LIST.setValue(list.toImmutableList())
}