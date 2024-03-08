package com.example.juzzics.features.musics.ui

import android.app.Application
import android.content.ContentUris
import android.media.MediaPlayer
import android.provider.MediaStore
import android.util.Log
import com.example.juzzics.common.base.viewModel.Action
import com.example.juzzics.common.base.viewModel.BaseViewModel
import com.example.juzzics.common.base.viewModel.UiEvent
import com.example.juzzics.common.base.viewModel.ViewState
import com.example.juzzics.common.base.extensions.mapList
import com.example.juzzics.features.musics.domain.usecases.GetAllLocalMusicFilesUseCase
import com.example.juzzics.features.musics.ui.model.MusicFileUi
import com.example.juzzics.features.musics.ui.model.toUi


class MusicVM(
    getAllLocalMusicFilesUseCase: GetAllLocalMusicFilesUseCase,
    private val context: Application
) : BaseViewModel(
    states = mutableMapOf<String, Any>(
        MUSIC_LIST to ViewState<List<MusicFileUi>>(),
        MEDIA_PLAYER to ViewState<MediaPlayer>(),
        CLICKED_MUSIC to ViewState<MusicFileUi>(),
        IS_PLAYING to ViewState<Boolean>(),
        SCROLL_POSISION to ViewState<Int>(),
    )
) {
    companion object {
        const val MUSIC_LIST = "musicList"
        const val MEDIA_PLAYER = "mediaPlayer"
        const val CLICKED_MUSIC = "clickedMusic"
        const val IS_PLAYING = "isPlaying"
        const val SCROLL_POSISION = "Scroll_POSISION"
    }

    init {
        Log.d("myLog", "init MusicVm")
        launch { call(getAllLocalMusicFilesUseCase().mapList { it.toUi() }, MUSIC_LIST) }
    }

    private fun playMusic(musicFile: MusicFileUi?) {
        val mediaPlayer = MEDIA_PLAYER.state<MediaPlayer>()
        val musicList = MUSIC_LIST.state<List<MusicFileUi>>()
        val clickedMusic = CLICKED_MUSIC.state<MusicFileUi>()
        fun onSameMusicCLicked() {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer.pause()
                IS_PLAYING.setValue(false)
            } else {
                mediaPlayer?.start()
                IS_PLAYING.setValue(true)
            }
        }

        fun onNewMusicClicked() {
            musicFile?.let {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                MediaPlayer.create(
                    context, ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicFile.id
                    )
                ) saveIn MEDIA_PLAYER
                CLICKED_MUSIC.setValue(musicFile)
                musicList?.map {
                    if (it != musicFile) it.copy(isPlaying = false)
                    else it.copy(isPlaying = true)
                } saveIn MUSIC_LIST
                IS_PLAYING.setValue(true)
                MEDIA_PLAYER.state<MediaPlayer>()?.apply {
                    setOnCompletionListener { playNextOrPrev(true) }
                    start()
                }
            }
        }
        if (musicFile == clickedMusic) {
            onSameMusicCLicked()
        } else {
            onNewMusicClicked()
        }
    }

    private fun playNextOrPrev(next: Boolean) {
        val list = MUSIC_LIST.state<List<MusicFileUi>>()
        val curIndex = list?.indexOf(CLICKED_MUSIC.state<MusicFileUi>()?.copy(isPlaying = true))
        curIndex?.let {
            if (next) {
                if (it + 1 > list.size - 1) 0 else it + 1
            } else {
                if (it - 1 < 0) list.size - 1 else it - 1
            }
        }?.also {
            onAction(PlayMusicAction(list[it]))
            val pos = if (next) {
                if (it == 0) 0 else it - 1
            } else {
                when (it) {
                    0 -> 0
                    list.size -> list.size - 1
                    else -> it - 1
                }
            }
            SCROLL_POSISION.setValue(pos)
            ScrollToPositionUiEvent(pos).emit()
        }
    }

    private fun seekTo(position: Float) {
        val mediaPlayer = MEDIA_PLAYER.state<MediaPlayer>()
        mediaPlayer?.seekTo((position * mediaPlayer.duration.toFloat()).toInt())
    }

    private fun playOrPause() = playMusic(CLICKED_MUSIC.state())

    override fun onAction(action: Action) {
        when (action) {
            is PlayMusicAction -> playMusic(action.music)
            is PlayNextAction -> playNextOrPrev(true)
            is PlayPrevAction -> playNextOrPrev(false)
            is SeekToAction -> seekTo(action.position)
            is PlayOrPauseAction -> playOrPause()
        }
    }

    data class PlayMusicAction(val music: MusicFileUi) : Action
    data class SeekToAction(val position: Float) : Action
    object PlayNextAction : Action
    object PlayPrevAction : Action
    object PlayOrPauseAction : Action

    data class ScrollToPositionUiEvent(val position: Int) : UiEvent
}