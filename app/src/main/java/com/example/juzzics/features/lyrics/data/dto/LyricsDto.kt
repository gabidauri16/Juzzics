package com.example.juzzics.features.lyrics.data.dto

import com.example.juzzics.features.lyrics.domain.model.LyricsDomain
import com.google.gson.annotations.SerializedName

data class LyricsDto(
    @SerializedName("lyrics")
    val lyrics: String
) {
    fun toDomain() = LyricsDomain(lyrics)
}