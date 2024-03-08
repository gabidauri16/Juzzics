package com.example.juzzics.features.musics.ui.model

import android.net.Uri
import com.example.juzzics.features.musics.domain.model.MusicFileDomain

data class MusicFileUi(
    val id: Long,
    val title: String?,
    val artist: String?,
    val data: String?,
    val duration: Long,
    val icon: Uri,
    val isPlaying: Boolean = false
)

fun MusicFileDomain.toUi() = MusicFileUi(id, title, artist, data, duration, icon, isPlaying)