package com.example.juzzics.musics.data.repo

import com.example.juzzics.musics.data.localProvider.MusicLocalProvider
import com.example.juzzics.musics.domain.model.MusicFileModel
import com.example.juzzics.musics.domain.repo.MusicRepo

class MusicRepoImpl(private val musicLocalProvider: MusicLocalProvider) : MusicRepo {
    override suspend fun getAllLocalMusicFiles(): Result<List<MusicFileModel>> {
        return musicLocalProvider.getAllLocalMusicFiles()
    }
}