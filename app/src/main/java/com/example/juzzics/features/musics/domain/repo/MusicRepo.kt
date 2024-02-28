package com.example.juzzics.features.musics.domain.repo

import com.example.juzzics.features.musics.domain.model.MusicFileModel

interface MusicRepo {
    suspend fun getAllLocalMusicFiles(): Result<List<MusicFileModel>>
}