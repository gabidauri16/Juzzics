package com.example.juzzics.features.musics.ui.vm

import android.app.Application
import android.media.MediaPlayer
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.juzzics.common.base.extensions.mapList
import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.BaseViewModel
import com.example.juzzics.common.base.viewModel.UiEvent
import com.example.juzzics.common.base.viewModel.ViewState
import com.example.juzzics.features.musics.domain.usecases.GetAllLocalMusicFilesUseCase
import com.example.juzzics.features.musics.ui.vm.logics.PlayMusicLogic
import com.example.juzzics.features.musics.ui.vm.logics.PlayNextOrPrevLogic
import com.example.juzzics.features.musics.ui.model.MusicFileUi
import com.example.juzzics.features.musics.ui.model.toUi


class MusicVM(
    private val context: Application,
    getAllLocalMusicFilesUseCase: GetAllLocalMusicFilesUseCase,
    private val playMusicLogic: PlayMusicLogic,
    private val playNextOrPrevLogic: PlayNextOrPrevLogic
) : BaseViewModel(
    states = mutableMapOf<String, Any>(
        MUSIC_LIST to ViewState<List<MusicFileUi>>(),
        MEDIA_PLAYER to ViewState<MediaPlayer>(),
        CLICKED_MUSIC to ViewState<MusicFileUi>(),
        IS_PLAYING to ViewState<Boolean>(),
        SCROLL_POSITION to ViewState<Int>(),
    )
) {
    companion object {
        const val MUSIC_LIST = "musicList"
        const val MEDIA_PLAYER = "mediaPlayer"
        const val CLICKED_MUSIC = "clickedMusic"
        const val IS_PLAYING = "isPlaying"
        const val SCROLL_POSITION = "Scroll_POSISION"
    }

    init {
        launch { call(getAllLocalMusicFilesUseCase().mapList { it.toUi() }, MUSIC_LIST) }
    }

    private fun playMusic(musicFile: MusicFileUi?) = playMusicLogic(this, musicFile, context)


    private fun playNextOrPrev(next: Boolean) = playNextOrPrevLogic(this, next)


    private fun seekTo(position: Float) {
        val mediaPlayer = MEDIA_PLAYER<MediaPlayer>()
        mediaPlayer?.seekTo((position * mediaPlayer.duration.toFloat()).toInt())
    }

    private fun playOrPause() = playMusic(CLICKED_MUSIC())

    override fun onAction(action: Action) {
        when (action) {
            is PlayMusicAction -> playMusic(action.music)
            is PlayNextAction -> playNextOrPrev(true)
            is PlayPrevAction -> playNextOrPrev(false)
            is SeekToAction -> seekTo(action.position)
            is PlayOrPauseAction -> playOrPause()
            is OnDragEndAction -> onDragEnd(action.list)
        }
    }

    private fun onDragEnd(list: SnapshotStateList<MusicFileUi>) {
        MUSIC_LIST.setValue(list.toList())
    }

    data class PlayMusicAction(val music: MusicFileUi) : Action
    data class SeekToAction(val position: Float) : Action
    object PlayNextAction : Action
    object PlayPrevAction : Action
    object PlayOrPauseAction : Action
    data class OnDragEndAction(val list: SnapshotStateList<MusicFileUi>) : Action

    data class ScrollToPositionUiEvent(val position: Int) : UiEvent
}