package com.example.juzzics.musics.data.localProvider

import com.example.juzzics.musics.domain.model.MusicFileModel

interface MusicLocalProvider {
    suspend fun getAllLocalMusicFiles(): Result<List<MusicFileModel>>
}