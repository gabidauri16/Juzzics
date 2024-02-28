package com.example.juzzics.features.musics.data.localProvider

import com.example.juzzics.features.musics.domain.model.MusicFileModel

interface MusicLocalProvider {
    suspend fun getAllLocalMusicFiles(): Result<List<MusicFileModel>>
}