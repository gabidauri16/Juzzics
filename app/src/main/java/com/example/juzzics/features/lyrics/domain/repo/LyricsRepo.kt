package com.example.juzzics.features.lyrics.domain.repo

import com.example.juzzics.features.lyrics.domain.model.LyricsDomain

interface LyricsRepo {
    suspend fun getLyrics(artist: String, title: String): Result<LyricsDomain>
}