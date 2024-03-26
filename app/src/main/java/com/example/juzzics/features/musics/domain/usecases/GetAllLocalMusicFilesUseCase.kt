package com.example.juzzics.features.musics.domain.usecases

import com.example.juzzics.common.base.ReturnUseCase
import com.example.juzzics.features.musics.domain.model.MusicFileDomain
import com.example.juzzics.features.musics.domain.repo.MusicRepo

class GetAllLocalMusicFilesUseCase(private val repo: MusicRepo) :
    ReturnUseCase<List<MusicFileDomain>> {
    override suspend fun invoke(): Result<List<MusicFileDomain>> = repo.getAllLocalMusicFiles()
}