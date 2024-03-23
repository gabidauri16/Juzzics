package com.example.juzzics.features.musics.ui.vm.logics

import android.media.MediaPlayer
import com.example.juzzics.features.musics.ui.vm.MusicVM
import com.example.juzzics.features.musics.ui.vm.MusicVM.Companion.MEDIA_PLAYER

fun MusicVM.seekTo(position: Float) {
    val mediaPlayer = MEDIA_PLAYER<MediaPlayer>()
    mediaPlayer?.seekTo((position * mediaPlayer.duration.toFloat()).toInt())
}