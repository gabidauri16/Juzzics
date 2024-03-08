package com.example.juzzics.features.musics.data.localProvider

import com.example.juzzics.features.musics.data.model.MusicFileDto

interface MusicLocalProvider {
    suspend fun getAllLocalMusicFiles(): Result<List<MusicFileDto>>
}