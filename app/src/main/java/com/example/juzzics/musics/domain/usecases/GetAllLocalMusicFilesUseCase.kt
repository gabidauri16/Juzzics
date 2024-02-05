package com.example.juzzics.musics.domain.usecases

import com.example.juzzics.common.base.BaseOnlyReturnUseCase
import com.example.juzzics.musics.domain.model.MusicFileModel
import com.example.juzzics.musics.domain.repo.MusicRepo
import kotlinx.coroutines.delay

class GetAllLocalMusicFilesUseCase(private val repo: MusicRepo) :
    BaseOnlyReturnUseCase<List<MusicFileModel>> {
    override suspend fun invoke(): Result<List<MusicFileModel>> {
        return repo.getAllLocalMusicFiles()
    }
}