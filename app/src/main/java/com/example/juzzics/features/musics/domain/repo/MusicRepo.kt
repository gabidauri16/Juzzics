package com.example.juzzics.features.musics.domain.repo

import com.example.juzzics.features.musics.domain.model.MusicFileDomain

interface MusicRepo {
    suspend fun getAllLocalMusicFiles(): Result<List<MusicFileDomain>>
}