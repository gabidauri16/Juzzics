package com.example.juzzics.features.musics.ui.vm.logics

import android.media.MediaPlayer
import com.example.juzzics.features.musics.ui.vm.MusicVM

fun MusicVM.seekTo(position: Float) {
    val mediaPlayer = MusicVM.MEDIA_PLAYER<MediaPlayer>()
    mediaPlayer?.seekTo((position * mediaPlayer.duration.toFloat()).toInt())
}