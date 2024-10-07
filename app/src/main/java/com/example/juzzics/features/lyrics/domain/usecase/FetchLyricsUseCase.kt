package com.example.juzzics.features.lyrics.domain.usecase

import com.example.juzzics.common.base.Base2UseCase
import com.example.juzzics.features.lyrics.domain.model.LyricsDomain
import com.example.juzzics.features.lyrics.domain.repo.LyricsRepo

class FetchLyricsUseCase(private val repo: LyricsRepo) :
    Base2UseCase<String, String, LyricsDomain> {
    override suspend fun invoke(artist: String, title: String) = repo.getLyrics(artist, title)
}
