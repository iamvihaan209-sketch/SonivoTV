package com.vihaan.sonivo.ui.nowplaying

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vihaan.sonivo.data.MusicRepository
import com.vihaan.sonivo.data.Song
import com.vihaan.sonivo.playback.PlaybackConnection
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NowPlayingViewModel(
    private val repository: MusicRepository,
    private val playbackConnection: PlaybackConnection
) : ViewModel() {
    val player = playbackConnection.player

    val songs: StateFlow<List<Song>> = repository.getSongs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration = _duration.asStateFlow()

    private val _currentSongTitle = MutableStateFlow("Not Playing")
    val currentSongTitle = _currentSongTitle.asStateFlow()

    private val _currentArtist = MutableStateFlow("Unknown Artist")
    val currentArtist = _currentArtist.asStateFlow()

    private val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }

        override fun onPlaybackStateChanged(state: Int) {
            updatePlaybackInfo()
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            updatePlaybackInfo()
        }
    }

    init {
        viewModelScope.launch {
            player.collect { p ->
                p?.addListener(listener)
                updatePlaybackInfo()
            }
        }

        viewModelScope.launch {
            while (true) {
                player.value?.let { p ->
                    if (p.isPlaying) {
                        _currentPosition.value = p.currentPosition
                    }
                }
                delay(1000)
            }
        }
    }

    private fun updatePlaybackInfo() {
        player.value?.let { p ->
            _duration.value = p.duration.coerceAtLeast(0L)
            _currentSongTitle.value = p.mediaMetadata.title?.toString() ?: "Not Playing"
            _currentArtist.value = p.mediaMetadata.artist?.toString() ?: "Unknown Artist"
            _isPlaying.value = p.isPlaying
        }
    }

    fun playSong(song: Song) {
        val mediaItem = MediaItem.Builder()
            .setMediaId(song.id)
            .setUri(song.mediaUrl)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(song.title)
                    .setArtist(song.artist)
                    .setArtworkUri(Uri.parse(song.artworkUrl))
                    .build()
            )
            .build()
        
        player.value?.let { p ->
            p.setMediaItem(mediaItem)
            p.prepare()
            p.play()
        }
    }

    fun togglePlayPause() {
        player.value?.let { p ->
            if (p.isPlaying) p.pause() else p.play()
        }
    }

    fun skipToNext() {
        player.value?.seekToNext()
    }

    fun skipToPrevious() {
        player.value?.seekToPrevious()
    }

    override fun onCleared() {
        player.value?.removeListener(listener)
        super.onCleared()
    }
}
