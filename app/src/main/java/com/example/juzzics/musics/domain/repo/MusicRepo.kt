package com.example.juzzics.musics.domain.repo

import com.example.juzzics.musics.domain.model.MusicFileModel

interface MusicRepo {
    suspend fun getAllLocalMusicFiles(): Result<List<MusicFileModel>>
}