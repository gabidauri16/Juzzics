package com.example.juzzics.musics.domain.model

import android.net.Uri

data class MusicFileModel(
    val id: Long,
    val title: String?,
    val artist: String?,
    val data: String?,
    val duration: Long,
    val icon: Uri,
    val isPlaying: Boolean = false
)