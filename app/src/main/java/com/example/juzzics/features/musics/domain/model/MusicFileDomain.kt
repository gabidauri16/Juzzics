package com.example.juzzics.features.musics.domain.model

import android.net.Uri

data class MusicFileDomain(
    val id: Long,
    val title: String?,
    val artist: String?,
    val data: String?,
    val duration: Long,
    val icon: Uri,
    val isPlaying: Boolean = false
)