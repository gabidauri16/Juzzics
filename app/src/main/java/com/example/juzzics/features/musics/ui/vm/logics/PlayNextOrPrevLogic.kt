package com.example.juzzics.features.musics.ui.vm.logics

import com.example.juzzics.common.base.extensions.andWith
import com.example.juzzics.common.base.viewModel.ViewModelsLogic
import com.example.juzzics.common.base.viewModel.emit
import com.example.juzzics.features.musics.ui.vm.MusicVM
import com.example.juzzics.features.musics.ui.model.MusicFileUi

class PlayNextOrPrevLogic : ViewModelsLogic {
    operator fun invoke(vm: MusicVM, next: Boolean) {
        vm.andWith(MusicVM) {
            val list = MUSIC_LIST<List<MusicFileUi>>()
//            val curIndex = list?.indexOf(CLICKED_MUSIC.state<MusicFileUi>()?.copy(isPlaying = true))
            val curMusic = list?.find { it.id == CLICKED_MUSIC<MusicFileUi>()?.id }
            val curIndex = list?.indexOf(curMusic)
            curIndex?.let {
                if (next) {
                    if (it + 1 > list.size - 1) 0 else it + 1
                } else {
                    if (it - 1 < 0) list.size - 1 else it - 1
                }
            }?.also {
                onAction(MusicVM.PlayMusicAction(list[it]))
                val pos = if (next) {
                    if (it == 0) 0 else it - 1
                } else {
                    when (it) {
                        0 -> 0
                        list.size -> list.size - 1
                        else -> it - 1
                    }
                }
                SCROLL_POSITION.setValue(pos)
                MusicVM.ScrollToPositionUiEvent(pos).emit()
            }
        }
    }
}