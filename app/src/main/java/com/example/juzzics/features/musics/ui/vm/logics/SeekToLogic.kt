package com.example.juzzics.features.musics.ui.vm.logics

import android.media.MediaPlayer
import com.example.juzzics.features.musics.ui.vm.MusicVM
import com.example.juzzics.features.musics.ui.vm.MusicVM.Companion.MEDIA_PLAYER

fun MusicVM.seekTo(position: Float) {
    MEDIA_PLAYER<MediaPlayer>()?.apply {
        seekTo((position * duration.toFloat()).toInt())
    }
}