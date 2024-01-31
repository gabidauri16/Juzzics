package com.example.juzzics.musics.domain.model

data class MusicFileModel(
    val id: Long,
    val title: String?,
    val artist: String?,
    val data: String?,
    val duration: Long,
    val isPlaying: Boolean = false
)