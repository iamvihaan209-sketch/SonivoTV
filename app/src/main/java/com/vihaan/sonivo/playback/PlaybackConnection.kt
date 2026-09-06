package com.vihaan.sonivo.playback

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlaybackConnection(context: Context) {
    private val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
    private var controllerFuture: ListenableFuture<MediaController>? = null
    
    private val _player = MutableStateFlow<Player?>(null)
    val player = _player.asStateFlow()

    init {
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture?.addListener({
            _player.value = controllerFuture?.get()
        }, MoreExecutors.directExecutor())
    }

    fun release() {
        controllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        _player.value = null
    }
}
