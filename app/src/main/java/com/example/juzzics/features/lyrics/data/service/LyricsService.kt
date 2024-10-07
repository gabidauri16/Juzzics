package com.example.juzzics.features.lyrics.data.service

import com.example.juzzics.features.lyrics.data.dto.LyricsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LyricsService {
    @GET("v1/{artist}/{title}")
    suspend fun getLyrics(
        @Path("artist") artist: String,
        @Path("title") title: String
    ): Response<LyricsDto>
}

