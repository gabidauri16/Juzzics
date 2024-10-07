package com.example.juzzics.features.lyrics.ui.vm.logics

import com.example.juzzics.features.lyrics.ui.vm.FetchLyricsVM
import com.example.juzzics.features.lyrics.ui.vm.FetchLyricsVM.Companion.ARTIST
import com.example.juzzics.features.lyrics.ui.vm.FetchLyricsVM.Companion.LYRICS
import com.example.juzzics.features.lyrics.ui.vm.FetchLyricsVM.Companion.TITLE

fun FetchLyricsVM.fetchLyrics() =
    launch(emitErrorMsgAction = true) { call(fetchLyricsUseCase(!ARTIST, !TITLE), LYRICS) }
