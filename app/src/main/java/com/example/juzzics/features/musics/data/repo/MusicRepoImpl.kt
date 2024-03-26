package com.example.juzzics.features.musics.data.repo

import com.example.juzzics.common.base.extensions.mapList
import com.example.juzzics.features.musics.data.localProvider.MusicLocalProvider
import com.example.juzzics.features.musics.data.model.toDomain
import com.example.juzzics.features.musics.domain.model.MusicFileDomain
import com.example.juzzics.features.musics.domain.repo.MusicRepo

class MusicRepoImpl(private val musicLocalProvider: MusicLocalProvider) : MusicRepo {
    override suspend fun getAllLocalMusicFiles(): Result<List<MusicFileDomain>> =
        musicLocalProvider.getAllLocalMusicFiles().mapList { it.toDomain() }
}