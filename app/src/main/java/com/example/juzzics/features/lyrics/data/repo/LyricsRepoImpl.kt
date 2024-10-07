package com.example.juzzics.features.lyrics.data.repo

import com.example.juzzics.features.lyrics.data.service.LyricsService
import com.example.juzzics.features.lyrics.domain.repo.LyricsRepo

class LyricsRepoImpl(private val lyricsService: LyricsService) : LyricsRepo {
    override suspend fun getLyrics(artist: String, title: String) = runCatching {
        lyricsService.getLyrics(artist, title).body()!!.toDomain()
    }
}