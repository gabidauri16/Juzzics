package com.example.juzzics.features.lyrics.ui.vm

import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.BaseViewModel
import com.example.juzzics.common.base.viewModel.State
import com.example.juzzics.features.lyrics.domain.model.LyricsDomain
import com.example.juzzics.features.lyrics.domain.usecase.FetchLyricsUseCase
import com.example.juzzics.features.lyrics.ui.vm.logics.fetchLyrics

class FetchLyricsVM(
    val fetchLyricsUseCase: FetchLyricsUseCase
) : BaseViewModel(
    mutableMapOf(
        LYRICS to State<LyricsDomain>(),
        ARTIST to State("nightwish"),
        TITLE to State("ghost love score")
    )
) {
    companion object {
        const val LYRICS = "Lyrics"
        const val ARTIST = "Artist"
        const val TITLE = "Title"
    }

    override fun onAction(action: Action) {
        when (action) {
            is FetchLyricsAction -> fetchLyrics()
            is UpdateArtistAction -> ARTIST(action.value)
            is UpdateTitleAction -> TITLE(action.value)
        }
    }

    data object FetchLyricsAction : Action
    data class UpdateArtistAction(val value: String) : Action
    data class UpdateTitleAction(val value: String) : Action
}