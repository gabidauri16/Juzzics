package com.example.juzzics.musics.ui

import android.app.Application
import android.content.ContentUris
import android.media.MediaPlayer
import android.provider.MediaStore
import com.example.juzzics.common.base.Action
import com.example.juzzics.common.base.BaseViewModel
import com.example.juzzics.common.base.UiEvent
import com.example.juzzics.common.base.ViewState
import com.example.juzzics.musics.domain.model.MusicFileModel
import com.example.juzzics.musics.domain.usecases.GetAllLocalMusicFilesUseCase

class MusicVM(
    getAllLocalMusicFilesUseCase: GetAllLocalMusicFilesUseCase,
    private val context: Application
) : BaseViewModel(
    states = mutableMapOf<String, Any>(
        MUSIC_LIST to ViewState<List<MusicFileModel>>(),
        MEDIA_PLAYER to ViewState<MediaPlayer>(),
        CLICKED_MUSIC to ViewState<MusicFileModel>(),
        IS_PLAYING to ViewState<Boolean>(),
    )
) {
    companion object {
        const val MUSIC_LIST = "musicList"
        const val MEDIA_PLAYER = "mediaPlayer"
        const val CLICKED_MUSIC = "clickedMusic"
        const val IS_PLAYING = "isPlaying"
    }

    init {
        launch { call(getAllLocalMusicFilesUseCase(), MUSIC_LIST) }
    }

    private fun playMusic(musicFile: MusicFileModel?) {
        val mediaPlayer = MEDIA_PLAYER.typeOf<MediaPlayer>()
        val musicList = MUSIC_LIST.typeOf<List<MusicFileModel>>()
        val clickedMusic = CLICKED_MUSIC.typeOf<MusicFileModel>()
        fun onSameMusicCLicked() {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer.pause()
                IS_PLAYING.setData(false)
            } else {
                mediaPlayer?.start()
                IS_PLAYING.setData(true)
            }
        }

        fun onNewMusicClicked() {
            musicFile?.let {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                MEDIA_PLAYER.setData(
                    MediaPlayer.create(
                        context, ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicFile.id
                        )
                    )
                )
                CLICKED_MUSIC.setData(musicFile)
                MUSIC_LIST.setData(
                    musicList?.map {
                        if (it != musicFile) it.copy(isPlaying = false)
                        else it.copy(isPlaying = true)
                    }
                )
                IS_PLAYING.setData(true)
                MEDIA_PLAYER.typeOf<MediaPlayer>()?.apply {
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
        val list = MUSIC_LIST.typeOf<List<MusicFileModel>>()
        val curIndex = list?.indexOf(CLICKED_MUSIC.typeOf<MusicFileModel>()?.copy(isPlaying = true))
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
            ScrollToPositionUiEvent(pos).emit()
        }
    }

    private fun seekTo(position: Float) {
        val mediaPlayer = MEDIA_PLAYER.typeOf<MediaPlayer>()
        mediaPlayer?.seekTo((position * mediaPlayer.duration.toFloat()).toInt())
    }

    private fun playOrPause() = playMusic(CLICKED_MUSIC.typeOf())

    override fun onAction(action: Action) {
        when (action) {
            is PlayMusicAction -> playMusic(action.music)
            is PlayNextAction -> playNextOrPrev(true)
            is PlayPrevAction -> playNextOrPrev(false)
            is SeekToAction -> seekTo(action.position)
            is PlayOrPauseAction -> playOrPause()
        }
    }

    data class PlayMusicAction(val music: MusicFileModel) : Action
    data class SeekToAction(val position: Float) : Action
    object PlayNextAction : Action
    object PlayPrevAction : Action
    object PlayOrPauseAction : Action

    data class ScrollToPositionUiEvent(val position: Int) : UiEvent
}